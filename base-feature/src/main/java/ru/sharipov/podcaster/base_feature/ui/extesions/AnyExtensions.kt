package ru.sharipov.podcaster.base_feature.ui.extesions

/**
 * Производит проверку `this` на принадлежность к любому из `items`.
 *
 * @return `true` -> если имеется хоть одно совпадение [Any.equals].
 * */
fun Any.isOneOf(vararg items: Any): Boolean {
    return items.any(::equals)
}
