package ru.sharipov.podcaster.base_feature.ui.extesions

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.annotation.*

/**
 * Конвертация `density point'ов` в `пиксели`.
 * */
fun Activity.dpToPx(value: Int): Int {
    return applicationContext.dpToPx(value)
}

/**
 * Конвертация `density point'ов` в `пиксели`.
 * */
fun Activity.dpToPx(value: Long): Int {
    return applicationContext.dpToPx(value)
}

/**
 * Конвертация `density point'ов` в `пиксели`.
 * */
fun Activity.dpToPx(value: Float): Int {
    return applicationContext.dpToPx(value)
}

/**
 * Извлечение количественной строки с одним аргументом из ресурсов.
 * */
fun Activity.quantityString(@PluralsRes id: Int, quantity: Int): String {
    return applicationContext.quantityString(id, quantity)
}

/**
 * Извлечение количественной строки из ресурсов.
 * */
fun Activity.quantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
    return applicationContext.quantityString(id, quantity, *formatArgs)
}

/**
 * Извлечение цвета из ресурсов.
 * */
fun Activity.color(@ColorRes id: Int): Int {
    return applicationContext.color(id)
}

/**
 * Извлечение `dimen` из ресурсов.
 * */
fun Activity.dimen(@DimenRes id: Int): Int {
    return applicationContext.dimen(id)
}

/**
 * Извлечение `drawable` из ресурсов.
 * */
fun Activity.drawable(@DrawableRes id: Int): Drawable? {
    return applicationContext.drawable(id)
}

/**
 * Извлечение строкового массива из ресурсов.
 * */
fun Activity.stringArr(@ArrayRes id: Int): Array<String> {
    return applicationContext.stringArr(id)
}

/**
 * Извлечение целочисленного массива из ресурсов.
 * */
fun Activity.intArr(@ArrayRes id: Int): IntArray {
    return applicationContext.intArr(id)
}

/**
 * Устанавливает цвет текста статус бара
 *
 * @param isLight true если необходимо установить светлый текст, иначе false
 */
fun Activity.setStatusBarTheme(isLight: Boolean) {
    if (isLight) setStatusBarLight() else setStatusBarDark()
}

fun Activity.setStatusBarLight() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val flags = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
}

fun Activity.setStatusBarDark() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val flags = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

/**
 * Установка светлых иконок в navigation-баре
 */
fun Activity.setNavigationBarLight() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val flags = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility =
            flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
    }
}

/**
 * Установка темных иконок в navigation-баре
 */
fun Activity.setNavigationBarDark() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val flags = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}
