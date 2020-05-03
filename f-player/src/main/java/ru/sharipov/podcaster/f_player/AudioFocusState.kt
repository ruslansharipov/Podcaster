package ru.sharipov.podcaster.f_player

import android.media.AudioManager.*

enum class AudioFocusState(vararg val ids: Int) {
    GAINED(AUDIOFOCUS_GAIN),
    DUCK(AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK),
    LOSS(AUDIOFOCUS_LOSS, AUDIOFOCUS_LOSS_TRANSIENT);

    companion object {

        fun getBy(id: Int): AudioFocusState {
            return values().firstOrNull { focusState -> focusState.ids.contains(id) } ?: LOSS
        }
    }
}