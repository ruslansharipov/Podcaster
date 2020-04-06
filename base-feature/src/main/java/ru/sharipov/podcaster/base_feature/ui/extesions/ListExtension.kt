package ru.sharipov.podcaster.base_feature.ui.extesions

/**
 * Добавить элемент в конец списка, если он не `null`.
 *
 * @return true - если элемент был добавлен в список, иначе - false.
 * */
fun <T : Any> MutableList<T>.addIfNonNull(item: T?): Boolean {
    return item?.let(::add) ?: false
}

/**
 * Добавить все `NonNull` элементы в конец списка.
 *
 * @return true - если хоть один элемент был добавлен в список, иначе - false.
 * */
fun <T : Any> MutableList<T>.addAllIfNonNull(items: List<T?>?): Boolean {
    if (items == null) return false
    val nonNullItems = items.filterNotNull()
    return addAllIfNotEmpty(nonNullItems)
}

fun <T : Any> MutableList<T>.addAllIfNotEmpty(items: List<T>): Boolean {
    if (items.isEmpty()) return false
    addAll(items)
    return true
}