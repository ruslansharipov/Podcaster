package ru.sharipov.podcaster.base_feature.ui.extesions

import android.content.Context
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import ru.sharipov.podcaster.base_feature.R
import ru.surfstudio.android.imageloader.ImageLoader
import ru.surfstudio.android.imageloader.ImageLoaderInterface
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
    ImageLoader
        .loadPicture(context, url, radiusRes, overlayRes, centerCrop)
        .intoView(this, onComplete)
}

fun ImageView.bindCirclePicture(
    url: String,
    @DrawableRes overlayRes: Int? = null,
    onComplete: () -> Unit = { }
) {
    ImageLoader.bindDefault(context, url, overlayRes)
        .circle()
        .intoView(this, onComplete)
}

fun ImageLoader.Companion.bindDefault(
    context: Context,
    url: String,
    @DrawableRes overlayRes: Int? = null
): ImageLoaderInterface {
    return with(context)
        .cacheStrategy(CacheStrategy.CACHE_ORIGINAL)
        .url(url)
        .centerCrop()
        .mask(overlayRes != null, overlayRes ?: 0, PorterDuff.Mode.DST_OVER)
        .preview(R.drawable.bg_placeholder_loading_rounded_8_dp)
        .error(R.drawable.bg_placeholder_loading_rounded_8_dp)
}

fun ImageLoader.Companion.loadPicture(
    context: Context,
    url: String,
    @DimenRes radiusRes: Int? = R.dimen.placeholder_picture_corner_radius,
    @DrawableRes overlayRes: Int? = null,
    centerCrop: Boolean = true
): ImageLoaderInterface {
    val radius = if (radiusRes != null) context.resources.getDimensionPixelOffset(radiusRes) else 0
    return bindDefault(context, url, overlayRes)
        .roundedCorners(isRoundedCorners = radiusRes != null, radiusPx = radius)
        .centerCrop(centerCrop)
}

private fun ImageLoaderInterface.intoView(view: ImageView, onComplete: () -> Unit = { }) {
    into(
        view = view,
        onCompleteLambda = { drawable, _ ->
            view.setImageDrawable(drawable)
            onComplete()
        }
    )
}
