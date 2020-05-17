package ru.sharipov.podcaster.f_player.media

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerServiceBus
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.domain.player.QueueData
import ru.sharipov.podcaster.f_player.playback.PlaybackInterface
import ru.surfstudio.android.logger.Logger
import java.util.concurrent.TimeUnit

private const val UPDATE_INTERVAL = 50L

class MediaManager constructor(
    private val playerServiceBus: PlayerServiceBus,
    private val queue: MediaQueue,
    private val playerCallback: PlaybackInterface.PlayerCallback,
    private val serviceCallback: PlaybackInterface.ServiceCallback
) : PlaybackInterface.ManagerCallback {

    private val compositeDisposable = CompositeDisposable()

    override fun onBuffer() {
        updatePlaybackState(PlaybackState.Buffering(queue.current), playerCallback.position)
    }

    override fun onPlay() {
        updatePlaybackState(PlaybackState.Playing(queue.current), playerCallback.position)
    }

    override fun onPause() {
        updatePlaybackState(PlaybackState.Paused(queue.current), playerCallback.position)
    }

    override fun onCompletion() {
        if (queue.hasNext()) {
            play(queue.next)
        } else {
            updatePlaybackState(PlaybackState.Completed(queue.current), playerCallback.position)
            playerCallback.complete()
        }
    }

    override fun onIdle() {
        updatePlaybackState(PlaybackState.Idle, 0)
    }

    override fun onError() {

    }

    fun subscribePosition() {
        val position = Observable.interval(UPDATE_INTERVAL, TimeUnit.MILLISECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ playerServiceBus.emitPosition(playerCallback.position) }, { /* Ignore exception */ })
        compositeDisposable.add(position)
    }

    fun unSubscribe() {
        compositeDisposable.clear()
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
        playerCallback.seekTo(position)
    }

    private fun handlePrevRequest() {
        val position = TimeUnit.MILLISECONDS.toSeconds(playerCallback.position)
        playerCallback.invalidateCurrent()
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
        playerCallback.pause()
    }

    private fun handleResumeRequest() {
        play(queue.current)
    }

    private fun handleStopRequest() {
        playerCallback.stop()
        updatePlaybackState(PlaybackState.Stopped, 0)
    }

    private fun play(media: Episode?) {
        playerCallback.play(media)
        serviceCallback.onPlaybackMediaChanged(media)
        updatePlaybackState(PlaybackState.Playing(media), playerCallback.position)
        onNextQueue()
    }

    private fun updatePlaybackState(playbackState: PlaybackState, position: Long) {
        serviceCallback.onPlaybackStateChanged(playbackState, position)
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

    fun onNewAction(action: PlayerAction?) {
        Logger.d("MediaManager.onNewAction: $action")
        when (action) {
            is PlayerAction.Play -> handlePlayRequest(action.media, action.index)
            is PlayerAction.Add -> handleAddRequest(action.media)
            is PlayerAction.Seek -> handleSeekRequest(action.position)
            is PlayerAction.Pause -> handlePauseRequest()
            is PlayerAction.Resume -> handleResumeRequest()
            is PlayerAction.Stop -> handleStopRequest()
            is PlayerAction.Next -> handleNextRequest()
            is PlayerAction.Previous -> handlePrevRequest()
        }
    }
}