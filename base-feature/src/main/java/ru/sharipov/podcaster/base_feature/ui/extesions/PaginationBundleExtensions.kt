package ru.sharipov.podcaster.base_feature.ui.extesions

import ru.sharipov.podcaster.base_feature.ui.pagination.PaginationBundle

/**
 * Extension-метод для трансформации PaginationBundle одного типа в другой.
 * */
fun <T, R> PaginationBundle<T>.transform(action: (T) -> R): PaginationBundle<R> {
    return PaginationBundle(list?.transform { action(it) }, state)
}
