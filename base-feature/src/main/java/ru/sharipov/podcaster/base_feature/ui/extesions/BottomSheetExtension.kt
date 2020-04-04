package ru.sharipov.podcaster.base_feature.ui.extesions

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * Добавление к [BottomSheetBehavior] лиснера, срабатывающего при изменении состояния скролла.
 *
 * @param listener лиснер, вызываемый при скролле. Содержит вью и новое состояние скролла
 */
fun <V : View> BottomSheetBehavior<V>.addScrollStateChangeListener(
    listener: (bottomSheet: View, newState: Int) -> Unit
) {
    addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            // без реализации
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            listener(bottomSheet, newState)
        }
    })
}
