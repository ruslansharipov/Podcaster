package ru.sharipov.podcaster.base_feature.ui.blur

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import eightbitlab.com.blurview.BlurView
import ru.sharipov.podcaster.base_feature.R

/**
 * BlurView с возможностью закругления краев.
 * */
class RoundedBlurView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BlurView(context, attrs, defStyleAttr) {

    private companion object {
        const val DEFAULT_CORNERS = 0f
    }

    private var internalClipPath: Path? = null
    private var internalCornerSize: Float = DEFAULT_CORNERS

    /**
     * Закругление углов в пикселях.
     * */
    var cornerSize: Int
        get() = internalCornerSize.toInt()
        set(value) {
            internalCornerSize = value.toFloat()
            invalidate()
        }

    init {
        obtainAttrs(attrs)
    }

    override fun draw(canvas: Canvas?) {
        canvas?.clipPath(getClipPathSafe())
        super.draw(canvas)
    }

    override fun invalidate() {
        internalClipPath = null
        super.invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        internalClipPath = null
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun obtainAttrs(attrs: AttributeSet?) {
        attrs ?: return
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundedBlurView)
        internalCornerSize = ta.getDimension(R.styleable.RoundedBlurView_cornerSize, DEFAULT_CORNERS)
        ta.recycle()
    }

    private fun getClipPathSafe(): Path {
        return internalClipPath ?: createClipPath().also { internalClipPath = it }
    }

    private fun createClipPath(): Path {
        val start = paddingStart.toFloat()
        val end = width - paddingEnd.toFloat()
        val top = paddingTop.toFloat()
        val bottom = height - paddingBottom.toFloat()
        return Path().apply {
            addRoundRect(start, top, end, bottom, internalCornerSize, internalCornerSize, Path.Direction.CW)
        }
    }
}