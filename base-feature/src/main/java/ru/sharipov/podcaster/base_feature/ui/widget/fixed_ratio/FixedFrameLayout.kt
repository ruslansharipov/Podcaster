package ru.sharipov.podcaster.base_feature.ui.widget.fixed_ratio

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * [FrameLayout] с фиксированным соотношением сторон
 */
open class FixedFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), FixedRatioView {

    override var ratio: Float = initRatio(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, findFixedMeasureSpec(widthMeasureSpec))
    }
}
