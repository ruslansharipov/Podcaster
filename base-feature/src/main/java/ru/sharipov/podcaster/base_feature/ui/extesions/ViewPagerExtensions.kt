package ru.sharipov.podcaster.base_feature.ui.extesions

import androidx.viewpager.widget.ViewPager

/**
 * Добавление к [ViewPager] лиснера, срабатывающего при изменении состояния скролла.
 *
 * @param listener лиснер, вызываемый при скролле. Содержит новое состояние скролла
 */
fun ViewPager.addScrollStateChangeListener(listener: (newState: Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            listener(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            // без реализации
        }

        override fun onPageSelected(position: Int) {
            // без реализации
        }
    })
}

/**
 * Добавление к [ViewPager] лиснера, срабатывающего при изменении выбранной позиции.
 *
 * @param listener лиснер, вызываемый при смене позиции. Содержит новую позицию
 */
fun ViewPager.addOnPageSelectedListener(listener: (position: Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            // без реализации
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            // без реализации
        }

        override fun onPageSelected(position: Int) {
            listener(position)
        }
    })
}
