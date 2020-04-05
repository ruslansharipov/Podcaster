package ru.sharipov.podcaster.base_feature.ui.extesions

import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderState
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi

/**
 * Extension-метод для получения состояния загрузки данных.
 * */
val RequestUi<*>.placeholderState: PlaceholderState?
    get() = load as? PlaceholderState

/**
 * Extension-метод для получения состояния загрузки данных.
 * */
val RequestUi<*>.isSwrLoading: Boolean
    get() = load is PlaceholderState.SwipeRefreshState

/**
 * Extension-метод для получения состояния результата загрузки данных.
 * */
val RequestUi<*>.hasData: Boolean
    get() = data != null

/**
 * Extension-метод для получения состояния процесса загрузки данных.
 * */
val RequestUi<*>.isLoading: Boolean
    get() = load?.isLoading ?: false

/**
 * Extension-метод для получения состояния ошибки загрузки данных.
 * */
val RequestUi<*>.hasError: Boolean
    get() = error != null