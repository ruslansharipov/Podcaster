package ru.sharipov.podcaster.base_feature.ui.extesions

import android.widget.SeekBar

fun SeekBar.setOnUserSeekChanges(
    listener: (progress: Int) -> Unit
) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                listener(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }

    })
}