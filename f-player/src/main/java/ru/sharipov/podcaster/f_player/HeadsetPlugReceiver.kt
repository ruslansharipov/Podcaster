package ru.sharipov.podcaster.f_player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager

class HeadsetPlugReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (!isInitialStickyBroadcast && intent.action == Intent.ACTION_HEADSET_PLUG) {
            val state = intent.getIntExtra("state", -1)
            // we care only about the case where the headphone gets unplugged
            if (state == 0) {
//                context.sendBroadcast(PAUSE)
            }
        } else if (intent.action == AudioManager.ACTION_AUDIO_BECOMING_NOISY) {
//            context.sendBroadcast(PAUSE)
        }
    }
}