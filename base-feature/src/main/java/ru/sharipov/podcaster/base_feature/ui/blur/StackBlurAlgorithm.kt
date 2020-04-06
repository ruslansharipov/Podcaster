package ru.sharipov.podcaster.base_feature.ui.blur

import android.graphics.Bitmap
import eightbitlab.com.blurview.BlurAlgorithm
import ru.surfstudio.android.imageloader.util.BlurUtil
import kotlin.math.roundToInt

class StackBlurAlgorithm : BlurAlgorithm {

    override fun destroy() = Unit

    override fun canModifyBitmap(): Boolean = true

    override fun getSupportedBitmapConfig(): Bitmap.Config = Bitmap.Config.ARGB_8888

    override fun blur(bitmap: Bitmap, blurRadius: Float): Bitmap {
        return BlurUtil
            .stackBlur(bitmap, blurRadius.roundToInt(), true)
            ?: bitmap
    }
}