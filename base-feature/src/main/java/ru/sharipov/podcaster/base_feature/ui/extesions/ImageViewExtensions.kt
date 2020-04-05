package ru.sharipov.podcaster.base_feature.ui.extesions

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import ru.sharipov.podcaster.base_feature.R
import ru.surfstudio.android.imageloader.ImageLoader
import ru.surfstudio.android.imageloader.data.CacheStrategy

/**
 * Установка цвета для [Drawable].
 *
 * @param color цвет (0xAARRGGBB).
 */
fun ImageView.setDrawableColor(color: Int) = drawable
    .mutate()
    .setColorFilter(color, PorterDuff.Mode.SRC_IN)

fun ImageView.bindPicture(
    url: String,
    @DimenRes radiusRes: Int? = R.dimen.placeholder_picture_corner_radius,
    @DrawableRes overlayRes: Int? = null,
    centerCrop: Boolean = true,
    onComplete: () -> Unit = { }
) {
    val radius = if (radiusRes != null) resources.getDimensionPixelOffset(radiusRes) else 0
    ImageLoader.with(context)
        .cacheStrategy(CacheStrategy.CACHE_ORIGINAL)
        .url(url)
        .preview(R.drawable.bg_placeholder_loading_rounded_8_dp)
        .error(R.drawable.bg_placeholder_loading_rounded_8_dp)
        .roundedCorners(isRoundedCorners = radiusRes != null, radiusPx = radius)
        .mask(overlayRes != null, overlayRes ?: 0, PorterDuff.Mode.DST_OVER)
        .centerCrop(centerCrop)
        .into(
            view = this,
            onCompleteLambda = { drawable, _ ->
                setImageDrawable(drawable)
                onComplete()
            }
        )
}
