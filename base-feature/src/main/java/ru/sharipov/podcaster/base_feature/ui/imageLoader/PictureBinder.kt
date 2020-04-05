package ru.sharipov.podcaster.base_feature.ui.imageLoader

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import ru.sharipov.podcaster.base_feature.R
import ru.surfstudio.android.imageloader.ImageLoader
import ru.surfstudio.android.imageloader.data.CacheStrategy

/**
 * Загружает изображения в ImageView c предустановленными параметрами preview, error, скругленными
 * углами и центрирования
 */
interface PictureBinder {

    /**
     * @param imageView в который будет загружено изображение, показаны плейсхолдеры на время
     * загрузки и в случае ошибки
     * @param url ссылка для загрузки изображения
     * @param radiusRes ресурс, в котором указан радиус скругления изображения
     * @param overlayRes ресурс, в котором лежит Drawable, которую следует расположить над картинкой.
     * @param onComplete экшн вызываемый после успешного завершения загрузки изображения
     */
    fun bindPicture(
        imageView: ImageView,
        url: String,
        @DimenRes radiusRes: Int? = R.dimen.placeholder_picture_corner_radius,
        @DrawableRes overlayRes: Int? = null,
        centerCrop: Boolean = true,
        onComplete: () -> Unit = { }
    ) {
        val radius = if (radiusRes != null) imageView.resources.getDimensionPixelOffset(radiusRes) else 0
        ImageLoader.with(imageView.context)
            .cacheStrategy(CacheStrategy.CACHE_ORIGINAL)
            .url(url)
            .preview(R.drawable.bg_placeholder_loading_rounded_8_dp)
            .error(R.drawable.bg_placeholder_loading_rounded_8_dp)
            .roundedCorners(isRoundedCorners = radiusRes != null, radiusPx = radius)
            .mask(overlayRes != null, overlayRes ?: 0, PorterDuff.Mode.DST_OVER)
            .centerCrop(centerCrop)
            .into(
                view = imageView,
                onCompleteLambda = { drawable, _ ->
                    imageView.setImageDrawable(drawable)
                    onComplete()
                }
            )
    }
}
