package ru.sharipov.podcaster.base_feature.ui.widget.fixed_ratio

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * [View] с возможностью установки [ratio]
 */
open class FixedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), FixedRatioView {

    override var ratio: Float = initRatio(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, findFixedMeasureSpec(widthMeasureSpec))
    }
}
