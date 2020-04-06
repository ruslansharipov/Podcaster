package ru.sharipov.podcaster.base_feature.ui.widget.fixed_ratio

import android.content.Context
import android.util.AttributeSet
import android.view.View
import ru.sharipov.podcaster.base_feature.R

/**
 * Интерфейс позволяющий задавать View соотношение сторон
 */
interface FixedRatioView {

    companion object {
        const val DEFAULT_RATIO = 1f
    }

    /**
     * Соотношение сторон - высоты к ширине
     * т.е например при высоте 200 и ширине 328
     * ratio == 0.61
     */
    var ratio: Float

    /**
     * Метод, определяющий MeasureSpec исходя из [ratio] и передаваемой [widthMeasureSpec]
     */
    fun findFixedMeasureSpec(widthMeasureSpec: Int): Int {
        val originalWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val newHeight = originalWidth * ratio
        return View.MeasureSpec.makeMeasureSpec(newHeight.toInt(), View.MeasureSpec.EXACTLY)
    }

    /**
     * Метод для инициализации [ratio]
     */
    fun initRatio(context: Context, attrs: AttributeSet?): Float {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FixedRatioView)
        val ratio = ta.getFloat(R.styleable.FixedRatioView_ratio, DEFAULT_RATIO)
        ta.recycle()
        return ratio
    }
}
