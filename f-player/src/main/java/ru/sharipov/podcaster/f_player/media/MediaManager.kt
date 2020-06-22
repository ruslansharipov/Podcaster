package ru.sharipov.podcaster.f_player.media

import android.content.Context
import android.media.AudioManager
import android.net.wifi.WifiManager
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerServiceBus
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.domain.player.QueueData
import ru.sharipov.podcaster.f_player.notification.AppNotificationManager
import ru.sharipov.podcaster.f_player.playback.*
import ru.sharipov.podcaster.i_history.HistoryInteractor
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class MediaManager constructor(
    context: Context,
    audioManager: AudioManager,
    wifiLock: WifiManager.WifiLock,
    private val playerServiceBus: PlayerServiceBus,
    private val notificationManager: AppNotificationManager,
    private val mediaSession: MediaSessionCompat,
    private val historyInteractor: HistoryInteractor
) : Player.EventListener {

    private companion object {
        const val POSITION_UPDATE_INTERVAL_MS = 1000L
        const val SKIP_FORWARD_INTERVAL_MS = 30 * 1000
        const val SKIP_REPLAY_INTERVAL_MS = 10 * 1000
    }

    private val positionDisposable: Disposable

    init {
        positionDisposable = Observable
            .interval(POSITION_UPDATE_INTERVAL_MS, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (player.isPlaying) {
                    val positionSec = player.positionMs.toSeconds()
                    historyInteractor.saveProgress(positionSec)

                    val bufferedPosition = player.bufferedPositionMs.toSeconds()
                    playerServiceBus.emitBufferedPosition(bufferedPosition)
                }
            }
    }

    private val queue = MediaQueue()
    private val player: PlayerInterface = AppPlayer(
        context = context,
        audioManager = audioManager,
        wifiLock = wifiLock,
        playerEventListener = this
    )

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            Player.STATE_IDLE -> updatePlaybackState(PlaybackState.Idle, 0)
            Player.STATE_READY -> onStateReady(playWhenReady)
            Player.STATE_BUFFERING -> onStateBuffering(playWhenReady)
            Player.STATE_ENDED -> onCompletion()
        }
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        updatePlaybackState(PlaybackState.Error(error), player.positionMs)
    }

    fun onNewAction(action: PlayerAction?) {
        Logger.d("MediaManager.onNewAction: $action")
        when (action) {
            is PlayerAction.Play -> handlePlayRequest(action.media, action.index)
            is PlayerAction.Add -> handleAddRequest(action.media)
            is PlayerAction.Seek -> handleSeekRequest(action.positionMs)
            is PlayerAction.Pause -> handlePauseRequest()
            is PlayerAction.Resume -> handleResumeRequest()
            is PlayerAction.Stop -> handleStopRequest()
            is PlayerAction.Next -> handleNextRequest()
            is PlayerAction.Previous -> handlePrevRequest()
            is PlayerAction.SkipForward -> handleSeekRequest(player.positionMs + SKIP_FORWARD_INTERVAL_MS)
            is PlayerAction.SkipReplay -> handleSeekRequest(player.positionMs - SKIP_REPLAY_INTERVAL_MS)
        }
    }

    fun onDestroy() {
        positionDisposable.dispose()
        mediaSession.release()
    }

    private fun onStateBuffering(playWhenReady: Boolean) {
        val newState: PlaybackState = if (playWhenReady) {
            PlaybackState.Buffering(queue.current)
        } else {
            PlaybackState.Paused(queue.current)
        }
        updatePlaybackState(newState, player.positionMs)
    }

    private fun onStateReady(playWhenReady: Boolean) {
        val currentMedia = queue.current
        val state: PlaybackState = if (playWhenReady) {
            PlaybackState.Playing(currentMedia)
        } else {
            PlaybackState.Paused(currentMedia)
        }
        updatePlaybackState(state, player.positionMs)
    }

    private fun onCompletion() {
        if (queue.hasNext()) {
            play(queue.next)
        } else {
            updatePlaybackState(PlaybackState.Completed(queue.current), player.positionMs)
            player.complete()
        }
    }

    private fun handlePlayRequest(media: List<Episode>, index: Int = 0) {
        if (media.isNotEmpty()) {
            queue.setQueue(media, index)
            play(queue.current)
        }
    }

    private fun handleAddRequest(media: Episode) {
        queue.addQueue(media)
        onNextQueue()
    }

    private fun handleRemoveRequest(media: Episode) {
        queue.removeQueue(media)
        onNextQueue()
    }

    private fun handleSeekRequest(position: Long) {
        player.seekTo(position)
    }

    private fun handlePrevRequest() {
        val position = TimeUnit.MILLISECONDS.toSeconds(player.positionMs)
        player.invalidateCurrent()
        if (position <= 3) {
            play(queue.previous)
        } else {
            play(queue.current)
        }
    }

    private fun handleNextRequest() {
        play(queue.next)
    }

    private fun handlePauseRequest() {
        player.pause()
    }

    private fun handleResumeRequest() {
        play(queue.current)
    }

    private fun handleStopRequest() {
        player.stop()
        updatePlaybackState(PlaybackState.Stopped, 0)
    }

    private fun play(media: Episode?) {
        val id = media?.id ?: EMPTY_STRING
        val savedProgress = historyInteractor.getProgress(id)
        val playerProgress = player.positionMs.toSeconds()
        val shouldRestorePosition = abs(playerProgress - savedProgress) > 1
        player.play(media)
        if (shouldRestorePosition) {
            player.seekTo(savedProgress * 1000L)
        }
        mediaSession.setMetadata(getMetadata(media))
        notificationManager.updateMedia(media)
        onNextQueue()
    }

    private fun updatePlaybackState(playbackState: PlaybackState, position: Long) {
        setSessionState(playbackState, position)
        notificationManager.updateState(playbackState)
        playerServiceBus.emitPlaybackState(playbackState)
    }

    private fun onNextQueue() {
        playerServiceBus.emitQueue(
            QueueData(
                queue.list,
                queue.currentIndex
            )
        )
    }

    private fun getMetadata(media: Episode?): MediaMetadataCompat {
        return MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_ART_URI, media?.image)
            .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, media?.title)
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, media?.podcastTitle)
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, media?.streamUrl)
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, media?.id)
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, media?.duration?.toLong() ?: 0)
            .build()
    }

    private fun setSessionState(state: PlaybackState, position: Long) {
        val stateCompat = PlaybackStateCompat.Builder()
            .setActions(getActions(state))
            .setState(
                when (state) {
                    is PlaybackState.Playing -> PlaybackStateCompat.STATE_PLAYING
                    is PlaybackState.Buffering -> PlaybackStateCompat.STATE_BUFFERING
                    is PlaybackState.Paused -> PlaybackStateCompat.STATE_PAUSED
                    is PlaybackState.Completed -> PlaybackStateCompat.STATE_PAUSED
                    is PlaybackState.Stopped -> PlaybackStateCompat.STATE_STOPPED
                    else -> PlaybackStateCompat.STATE_NONE
                }, position, 1.0f, SystemClock.elapsedRealtime()
            )
            .build()

        mediaSession.isActive = state is PlaybackState.Playing || state is PlaybackState.Buffering
        mediaSession.setPlaybackState(stateCompat)
    }

    private fun getActions(state: PlaybackState): Long {
        var actions = PlaybackStateCompat.ACTION_PLAY_PAUSE or
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                PlaybackStateCompat.ACTION_SET_REPEAT_MODE or
                PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE
        actions = if (state is PlaybackState.Playing || state is PlaybackState.Buffering) {
            actions or PlaybackStateCompat.ACTION_PAUSE
        } else {
            actions or PlaybackStateCompat.ACTION_PLAY
        }
        return actions
    }

    private fun Long.toSeconds(): Int {
        return (this / 1000).toInt()
    }
}