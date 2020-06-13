package ru.sharipov.podcaster.f_player.playback

import android.content.Context
import android.media.AudioManager
import android.net.Uri
import android.net.wifi.WifiManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import ru.sharipov.podcaster.base_feature.R

class AppPlayer(
    context: Context,
    audioManager: AudioManager,
    wifiLock: WifiManager.WifiLock,
    private val playerEventListener: Player.EventListener
) : BasePlayer(context, audioManager, wifiLock) {

    private var player: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
        context,
        DefaultRenderersFactory(context),
        DefaultTrackSelector(),
        DefaultLoadControl()
    ).apply {
        audioAttributes = AudioAttributes.Builder()
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()
    }
    
    override val bufferedPositionMs: Long
        get() = player.bufferedPosition

    override val isPlaying: Boolean
        get() = player.playWhenReady

    override val positionMs: Long
        get() = player.currentPosition

    override val duration: Long
        get() = player.duration

    override fun startPlayer() {
        play()
    }

    override fun pausePlayer() {
        player.playWhenReady = false
    }

    override fun stopPlayer() {
        player.release()
        player.removeListener(playerEventListener)
        player.playWhenReady = false
    }

    override fun resumePlayer() {
        player.playWhenReady = true
    }

    override fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }

    private fun play() {
        // TODO listener через который можно отслеживать загрузку данных
        val dataSourceFactory = DefaultDataSourceFactory(
            context, Util.getUserAgent(context, context.getString(R.string.app_name)), null
        )
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(currentMedia?.streamUrl))
        player.addListener(playerEventListener)
        player.prepare(mediaSource)
        player.playWhenReady = true
    }
}