package ru.sharipov.podcaster.base_feature.ui.extesions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.recyclerview.widget.RecyclerView

/**
 * Контекст `ViewHolder`.
 * */
val RecyclerView.ViewHolder.context: Context get() = itemView.context

/** Конвертация `density point'ов` в `пиксели`. */
fun RecyclerView.ViewHolder.dpToPx(value: Int): Int {
    return context.dpToPx(value)
}

/** Конвертация `density point'ов` в `пиксели`. */
fun RecyclerView.ViewHolder.dpToPx(value: Long): Int {
    return context.dpToPx(value)
}

/** Конвертация `density point'ов` в `пиксели`. */
fun RecyclerView.ViewHolder.dpToPx(value: Float): Int {
    return context.dpToPx(value)
}

/**
 * Извлечение строки из ресурсов.
 */
fun RecyclerView.ViewHolder.string(@StringRes id: Int, vararg formatArgs: Any): String {
    return context.getString(id, *formatArgs)
}

/**
 * Извлечение количественной строки с одним аргументом из ресурсов.
 * */
fun RecyclerView.ViewHolder.quantityString(@PluralsRes id: Int, quantity: Int): String {
    return context.quantityString(id, quantity)
}

/**
 * Извлечение количественной строки из ресурсов.
 * */
fun RecyclerView.ViewHolder.quantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
    return context.quantityString(id, quantity, *formatArgs)
}

/**
 * Извлечение цвета из ресурсов.
 */
fun RecyclerView.ViewHolder.color(@ColorRes id: Int): Int {
    return context.color(id)
}

/**
 * Извлечение `dimen` из ресурсов.
 */
fun RecyclerView.ViewHolder.dimen(@DimenRes id: Int): Int {
    return context.dimen(id)
}

/**
 * Извлечение `drawable` из ресурсов.
 */
fun RecyclerView.ViewHolder.drawable(@DrawableRes id: Int): Drawable? {
    return context.drawable(id)
}

/**
 * Извлечение строкового массива из ресурсов.
 */
fun RecyclerView.ViewHolder.stringArr(@ArrayRes id: Int): Array<String> {
    return context.stringArr(id)
}

/**
 * Извлечение числового массива из ресурсов.
 */
fun RecyclerView.ViewHolder.intArr(@ArrayRes id: Int): IntArray {
    return context.intArr(id)
}
