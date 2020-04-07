package ru.sharipov.podcaster.base_feature.ui.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Размер системных окон
 *
 * @param statusBar размер системного статус-бара
 * @param navigationBar размер navigation-бара
 * @param keyboard размер системной клавиатуры
 */
@Parcelize
data class SystemBarsSize(
    val statusBar: Int = 0,
    val navigationBar: Int = 0,
    val keyboard: Int = 0
) : Parcelable {

    val hasKeyboard: Boolean get() = keyboard != 0
    val isEmpty get() = statusBar == 0 && navigationBar == 0 && keyboard == 0
}
