package ru.sharipov.podcaster.base_feature.ui.extesions

import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderState
import ru.surfstudio.android.core.mvi.util.filterIsInstance
import ru.surfstudio.android.core.mvp.binding.rx.request.state.RequestState

/**
 * Прослушивание событий [SwipeRefreshState].
 * Когда мы получаем [SwipeRefreshState] - эмитим true, иначе - false,
 * таким образом управляем видимостью лоадера у SwipeRefreshLayout.
 */
fun <T> RequestState<T>.observeIsSwr(): Observable<Boolean> {
    return observeLoading()
        .map { it is PlaceholderState.SwipeRefreshState }
        .distinctUntilChanged()
}

/**
 * Прослушивание событий [PlaceholderState].
 */
fun <T> RequestState<T>.observePlaceholderState(): Observable<PlaceholderState> {
    return observeLoading().filterIsInstance<PlaceholderState>()
}
