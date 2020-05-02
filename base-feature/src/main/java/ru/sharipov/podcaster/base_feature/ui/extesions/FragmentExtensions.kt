package ru.sharipov.podcaster.base_feature.ui.extesions

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.*
import androidx.fragment.app.Fragment
import ru.surfstudio.android.utilktx.ktx.ui.context.getDisplayMetrics

/**
 * Конвертация `density point'ов` в `пиксели`.
 * */
fun Fragment.dpToPx(value: Int): Int {
    return requireContext().dpToPx(value)
}

/**
 * Конвертация `density point'ов` в `пиксели`.
 * */
fun Fragment.dpToPx(value: Long): Int {
    return requireContext().dpToPx(value)
}

/**
 * Конвертация `density point'ов` в `пиксели`.
 * */
fun Fragment.dpToPx(value: Float): Int {
    return requireContext().dpToPx(value)
}

/**
 * Извлечение количественной строки с одним аргументом из ресурсов.
 * */
fun Fragment.quantityString(@PluralsRes id: Int, quantity: Int): String {
    return requireContext().quantityString(id, quantity)
}

/**
 * Извлечение количественной строки из ресурсов.
 * */
fun Fragment.quantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
    return requireContext().quantityString(id, quantity, *formatArgs)
}

/**
 * Извлечение цвета из ресурсов.
 * */
fun Fragment.color(@ColorRes id: Int): Int {
    return requireContext().color(id)
}

/**
 * Извлечение `dimen` из ресурсов.
 * */
fun Fragment.dimen(@DimenRes id: Int): Int {
    return requireContext().dimen(id)
}

/**
 * Извлечение `drawable` из ресурсов.
 * */
fun Fragment.drawable(@DrawableRes id: Int): Drawable? {
    return requireContext().drawable(id)
}

/**
 * Извлечение строкового массива из ресурсов.
 * */
fun Fragment.stringArr(@ArrayRes id: Int): Array<String> {
    return requireContext().stringArr(id)
}

/**
 * Извлечение целочисленного массива из ресурсов.
 * */
fun Fragment.intArr(@ArrayRes id: Int): IntArray {
    return requireContext().intArr(id)
}

/**
 * Высота экрана в пикселях
 */
val Fragment.displayHeight: Int
    get() {
        return requireActivity()
            .getDisplayMetrics()
            .heightPixels
    }
