package ru.sharipov.podcaster.f_player.playback

import android.content.Context
import android.media.AudioManager
import android.net.Uri
import android.net.wifi.WifiManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class AppPlayer(
    context: Context,
    audioManager: AudioManager,
    wifiLock: WifiManager.WifiLock,
    private val dataSourceFactory: DefaultDataSourceFactory,
    private val player: SimpleExoPlayer
) : BasePlayer(context, audioManager, wifiLock) {

    private var playerEventListener: Player.EventListener? = null
    
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
        if (playerEventListener != null) {
            player.removeListener(playerEventListener!!)
        }
        player.playWhenReady = false
    }

    override fun resumePlayer() {
        player.playWhenReady = true
    }

    override fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }

    override fun setListener(listener: Player.EventListener) {
        playerEventListener = listener
    }

    private fun play() {
        val mediaSourceFactory = ExtractorMediaSource.Factory(dataSourceFactory)
        val mediaSource = mediaSourceFactory
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(currentMedia?.streamUrl))
        if (playerEventListener != null) {
            player.addListener(playerEventListener!!)
        }
        player.prepare(mediaSource)
        player.playWhenReady = true
    }
}