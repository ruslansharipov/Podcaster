package ru.sharipov.podcaster.base_feature.ui.widget.episode

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

/**
 * Span that replaces text with attached [drawable]
 * that aligned at the center of surrounding text
 */
class CenteredImageSpan(drawable: Drawable?) : ImageSpan(drawable) {
    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val drawable: Drawable = drawable
        canvas.save()

        var transY = bottom - drawable.bounds.bottom
        transY -= paint.fontMetricsInt.descent / 2

        canvas.translate(x, transY.toFloat())
        drawable.draw(canvas)
        canvas.restore()
    }
}