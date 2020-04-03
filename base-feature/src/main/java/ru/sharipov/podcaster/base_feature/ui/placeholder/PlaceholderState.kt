package ru.sharipov.podcaster.base_feature.ui.placeholder

import ru.surfstudio.android.core.mvp.binding.rx.request.data.Loading

/**
 * Состояния плейсхолдера
 */
sealed class PlaceholderState: Loading {

    object None : PlaceholderState() {
        override val isLoading: Boolean = false
    }

    object Error : PlaceholderState() {
        override val isLoading: Boolean = false
    }

    object Empty : PlaceholderState() {
        override val isLoading: Boolean = false
    }

    object NotFound : PlaceholderState() {
        override val isLoading: Boolean = false
    }

    object NoInternet : PlaceholderState() {
        override val isLoading: Boolean = false
    }

    object MainLoading : PlaceholderState() {
        override val isLoading: Boolean = true
    }

    object TransparentLoading : PlaceholderState() {
        override val isLoading: Boolean = true
    }

    object SwipeRefreshState : PlaceholderState() {
        override val isLoading: Boolean = true
    }
}