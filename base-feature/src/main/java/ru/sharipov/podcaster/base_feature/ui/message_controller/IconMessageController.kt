package ru.sharipov.podcaster.base_feature.ui.message_controller

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Интерфейс контроллера отображения сообщений с изображением
 * Максимальное количество линий задается в integers:design_snackbar_text_max_lines
 */
interface IconMessageController {

    companion object {
        private const val DEFAULT_SNACK_DURATION: Long = 3000
    }

    fun show(message: String,
             @ColorRes backgroundColor: Int? = null,
             @DrawableRes iconResId: Int? = null,
             durationMillis: Long = DEFAULT_SNACK_DURATION,
             listener: (view: View) -> Unit = {})

    fun show(@StringRes stringId: Int,
             @ColorRes backgroundColor: Int? = null,
             @DrawableRes iconResId: Int? = null,
             durationMillis: Long = DEFAULT_SNACK_DURATION,
             listener: (view: View) -> Unit = {})

    fun closeSnack()
}