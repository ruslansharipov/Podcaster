package ru.sharipov.podcaster.f_player

import android.annotation.TargetApi
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
@TargetApi(Build.VERSION_CODES.O)
class OreoAudioFocusHandler @Inject constructor(context: Context) {

    private var audioFocusRequest: AudioFocusRequest? = null
    private var audioManager: AudioManager? = null

    init {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    fun abandonAudioFocus() {
        if (audioFocusRequest != null) {
            audioManager?.abandonAudioFocusRequest(audioFocusRequest)
        }
    }

    fun requestAudioFocus(audioFocusChangeListener: AudioManager.OnAudioFocusChangeListener) {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setOnAudioFocusChangeListener(audioFocusChangeListener)
            .setAudioAttributes(audioAttributes)
            .build()

        audioManager?.requestAudioFocus(audioFocusRequest!!)
    }
}
