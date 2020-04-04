package ru.sharipov.podcaster.base_feature.ui.extesions

import android.graphics.PorterDuff
import androidx.annotation.DrawableRes
import ru.surfstudio.android.imageloader.ImageLoader

/**
 * Добавляет маску к изображению
 *
 * @param backgroundRes добавляемая маска
 */
fun ImageLoader.overlay(@DrawableRes backgroundRes: Int): ImageLoader {
    return mask(
        isOverlay = true,
        maskResId = backgroundRes,
        overlayMode = PorterDuff.Mode.DST_OVER
    )
}
