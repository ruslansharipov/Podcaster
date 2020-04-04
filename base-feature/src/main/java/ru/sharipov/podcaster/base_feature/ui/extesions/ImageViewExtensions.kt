package ru.sharipov.podcaster.base_feature.ui.extesions

import android.graphics.PorterDuff
import android.widget.ImageView

/**
 * Установка цвета для [Drawable].
 *
 * @param color цвет (0xAARRGGBB).
 */
fun ImageView.setDrawableColor(color: Int) = drawable
    .mutate()
    .setColorFilter(color, PorterDuff.Mode.SRC_IN)
