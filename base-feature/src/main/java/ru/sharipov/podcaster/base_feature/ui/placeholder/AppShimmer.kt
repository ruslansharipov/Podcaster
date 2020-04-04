package ru.sharipov.podcaster.base_feature.ui.placeholder

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.children
import io.supercharge.shimmerlayout.ShimmerLayout
import ru.sharipov.podcaster.base_feature.R

/**
 * Дефолтный класс контейнера с анимацией шиммера
 *
 * Взаимодействовать следует через свойство [isLoading].
 */
open class AppShimmer @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = R.attr.appShimmerStyle
) : ShimmerLayout(
        ContextThemeWrapper(context, R.style.AppShimmerStyle),
        attrs,
        defStyleAttr
) {

    open var isLoading: Boolean = false
        set(value) {
            field = value
            children.forEach { it.isClickable = !value }
            if (value) {
                showAnimation()
            } else {
                hideAnimation()
            }

        }

    override fun startShimmerAnimation() {
        isLoading = true
    }

    override fun stopShimmerAnimation() {
        isLoading = false
    }

    protected fun showAnimation() {
        super.startShimmerAnimation()
    }

    protected fun hideAnimation() {
        super.stopShimmerAnimation()
    }
}