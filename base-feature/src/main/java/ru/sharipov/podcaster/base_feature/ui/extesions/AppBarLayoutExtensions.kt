package ru.sharipov.podcaster.base_feature.ui.extesions

import android.view.View
import androidx.core.view.updatePadding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Устанавливает прозрачность разделителю реагируя на изменения сдвига [AppBarLayout]
 */
fun AppBarLayout.setupWithDivider(divider: View) {
    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        divider.alpha = abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
    })
}

/**
 * Корректирует положение `subtitleView` по горизонтали так,
 * чтобы он передвигался из состояния expanded в collapsed вместе с `title`.
 *
 * @param subtitleView View, которую следует перемещать вместе с title.
 * @param collapsedPaddingStartPx количество пикселей, на которое уезжает title при переходе в collapses-состояние.
 * */
fun AppBarLayout.setupWithSubtitle(subtitleView: View, collapsedPaddingStartPx: Int) {
    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->
        val ratio = abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
        val newPaddingLeft = (collapsedPaddingStartPx * ratio).roundToInt()
        subtitleView.updatePadding(left = newPaddingLeft)
    })
}
