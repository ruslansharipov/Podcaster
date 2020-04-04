package ru.sharipov.podcaster.base_feature.ui.extesions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ru.surfstudio.android.utilktx.util.ViewUtil

/** Конвертация `density point'ов` в `пиксели`. */
fun Context.dpToPx(value: Int): Int {
    return dpToPx(value.toFloat())
}

/** Конвертация `density point'ов` в `пиксели`. */
fun Context.dpToPx(value: Long): Int {
    return dpToPx(value.toFloat())
}

/** Конвертация `density point'ов` в `пиксели`. */
fun Context.dpToPx(value: Float): Int {
    return ViewUtil.convertDpToPx(this, value)
}

/**
 * Извлечение количественной строки с одним аргументом из ресурсов.
 * */
fun Context.quantityString(@PluralsRes id: Int, quantity: Int): String {
    return quantityString(id, quantity, quantity)
}

/**
 * Извлечение количественной строки из ресурсов.
 * */
fun Context.quantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
    return resources.getQuantityString(id, quantity, *formatArgs)
}

/**
 * Извлечение цвета из ресурсов.
 * */
fun Context.color(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

/**
 * Извлечение `dimen` из ресурсов.
 * */
fun Context.dimen(@DimenRes id: Int): Int {
    return resources.getDimensionPixelOffset(id)
}

/**
 * Извлечение `drawable` из ресурсов.
 * */
fun Context.drawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

/**
 * Извлечение строкового массива из ресурсов.
 * */
fun Context.stringArr(@ArrayRes id: Int): Array<String> {
    return resources.getStringArray(id)
}

/**
 * Извлечение целочисленного массива из ресурсов.
 * */
fun Context.intArr(@ArrayRes id: Int): IntArray {
    return resources.getIntArray(id)
}

/**
 * Извлечение целого числа из ресурсов
 */
fun Context.integer(@IntegerRes id: Int): Int {
    return resources.getInteger(id)
}

/**
 * Извлечение Float значений из dimen ресурсов.
 */
fun Context.dimenF(@DimenRes id: Int): Float {
    return ResourcesCompat.getFloat(resources, id)
}
