package ru.sharipov.podcaster.f_player.playback

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.wifi.WifiManager
import ru.sharipov.podcaster.domain.Episode

abstract class BasePlayer(
    protected val context: Context,
    private val audioManager: AudioManager,
    private val wifiLock: WifiManager.WifiLock
) : PlayerInterface, AudioManager.OnAudioFocusChangeListener {

    @Volatile
    protected var currentMedia: Episode? = null

    private var receiverRegistered = false
    private val audioBecomingNoisyIntent = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)

    private val audioNoisyReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY == intent.action) {
                pause()
            }
        }
    }

    abstract fun startPlayer()

    abstract fun pausePlayer()

    abstract fun resumePlayer()

    abstract fun stopPlayer()

    override fun play(media: Episode?) {
        if (media != null) {
            requestFocus()
            registerWifiLock()
            registerNoiseReceiver()
            if (media == currentMedia) {
                resumePlayer()
            } else {
                currentMedia = media
                startPlayer()
            }
        }
    }

    override fun invalidateCurrent() {
        currentMedia = null
    }

    override fun pause() {
        pausePlayer()
        unregisterWifiLock()
        unregisterNoiseReceiver()
    }

    override fun complete() {
        invalidateCurrent()
        unregisterWifiLock()
        unregisterNoiseReceiver()
    }

    override fun stop() {
        releaseFocus()
        unregisterWifiLock()
        unregisterNoiseReceiver()
        stopPlayer()
        invalidateCurrent()
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                play(currentMedia)
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> if (isPlaying) {
                pause()
            }
            AudioManager.AUDIOFOCUS_LOSS -> if (isPlaying) {
                pause()
            }
        }
    }

    private fun requestFocus() {
        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
    }

    private fun releaseFocus() {
        audioManager.abandonAudioFocus(this)
    }

    private fun registerWifiLock() {
        if (!wifiLock.isHeld) {
            wifiLock.acquire()
        }
    }

    private fun unregisterWifiLock() {
        if (wifiLock.isHeld) {
            wifiLock.release()
        }
    }

    private fun registerNoiseReceiver() {
        if(!receiverRegistered) {
            context.registerReceiver(audioNoisyReceiver, audioBecomingNoisyIntent)
            receiverRegistered = true
        }
    }

    private fun unregisterNoiseReceiver() {
        if(receiverRegistered) {
            context.unregisterReceiver(audioNoisyReceiver)
            receiverRegistered = false
        }
    }
}