package ru.sharipov.podcaster.base_feature.ui.pagination

import ru.sharipov.podcaster.base.datalist_date.MergeList
import ru.surfstudio.android.easyadapter.pagination.PaginationState

/**
 * Этот класс копирует функциональность PaginationBundle но для MergeList
 */
class MergePaginationBundle<T>(
    val list: MergeList<T>? = null,
    val state: PaginationState? = null
) {

    val hasData = !list.isNullOrEmpty()

    /**
     * Получение данных и paginationState в том случае, когда они оба не null
     *
     * @param onDataReadyCallback коллбек, в котором будут содержаться безопсные данные
     */
    fun safeGet(onDataReadyCallback: (MergeList<T>, PaginationState) -> Unit) {
        list ?: return
        state ?: return
        onDataReadyCallback(list, state)
    }
}