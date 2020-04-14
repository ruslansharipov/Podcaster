package ru.sharipov.podcaster.base_feature.ui.base.reducer

import ru.sharipov.podcaster.base.datalist_date.MergeList
import ru.sharipov.podcaster.base_feature.ui.pagination.MergePaginationBundle
import ru.sharipov.podcaster.base_feature.ui.pagination.PaginationBundle
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderState
import ru.sharipov.podcaster.domain.SortType
import ru.sharipov.podcaster.i_network.error.server.SpecificServerException
import ru.sharipov.podcaster.i_network.network.error.NoInternetException
import ru.surfstudio.android.core.mvi.ui.relation.StateEmitter
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.*
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.core.mvp.error.ErrorHandler
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import ru.surfstudio.android.rx.extension.scheduler.MainThreadImmediateScheduler

open class StateReducer(dependency: StateReducerDependency) : StateEmitter {

    protected val errorHandler: ErrorHandler = dependency.errorHandler

    protected fun <T> mapLoading(
        type: Request<T>,
        hasData: Boolean,
        isSwr: Boolean = false
    ): Loading {
        val isLoading = type is Request.Loading
        return when {
            isSwr ->
                SwipeRefreshLoading(isLoading)
            hasData ->
                TransparentLoading(isLoading)
            else ->
                MainLoading(isLoading)
        }
    }

    protected fun mapLoadState(
        type: Request<*>,
        hasDataBeforeRequest: Boolean,
        hasDataAfterRequest: Boolean,
        isSwr: Boolean
    ): PlaceholderState = when (type) {
        is Request.Success -> {
            if (hasDataAfterRequest) PlaceholderState.None else PlaceholderState.Empty
        }
        is Request.Error -> {
            val error = type.error
            when {
                hasDataAfterRequest -> PlaceholderState.None
                error is NoInternetException -> PlaceholderState.NoInternet
                else -> PlaceholderState.Error
            }
        }
        is Request.Loading -> {
            when {
                isSwr -> PlaceholderState.SwipeRefreshState
                hasDataBeforeRequest -> PlaceholderState.TransparentLoading
                else -> PlaceholderState.MainLoading
            }
        }
    }

    protected fun <T> mapError(
        type: Request<T>,
        hasData: Boolean,
        lastError: Throwable?,
        sideEffects: (Throwable) -> Unit = {}
    ): Throwable? {

        val error = when {
            hasData -> null
            type is Request.Error -> type.error.also(sideEffects)
            lastError != null -> lastError
            else -> null
        }

        // Показываем снек только тогда,
        // когда у нас не показывается ошибка, либо когда эта ошибка специфичная и требуется пояснение.
        val hasError = error != null
        if (type is Request.Error && !hasError) {
            handleErrorInMainThread(type.error)
        }

        return error
    }

    protected fun <T> mapData(request: Request<T>, data: T?): T? =
        if (request is Request.Success) {
            request.data
        } else {
            data
        }

    protected fun <T> mapDataList(
        request: Request<DataList<T>>,
        data: DataList<T>?,
        hasData: Boolean = data != null
    ): DataList<T>? =
        if (request is Request.Success) {
            if (hasData && request.data.nextPage != 1) { // мержим, если уже есть dataList + это не перезагрузка списка
                data?.merge(request.data)
            } else {
                request.data
            }
        } else {
            data
        }

    protected fun <T> mapMergeList(
        request: Request<MergeList<T>>,
        data: MergeList<T>?,
        isReload: Boolean,
        hasData: Boolean = data != null
    ): MergeList<T>? = if (request is Request.Success) {
        if (hasData && !isReload) { // мержим, если уже есть dataList + это не перезагрузка списка
            data?.merge(request.data)
        } else {
            request.data
        }
    } else {
        data
    }


    protected fun <T> mapPagination(
        request: Request<DataList<T>>,
        data: PaginationBundle<T>?
    ): PaginationBundle<T> {
        val hasData = data?.hasData ?: false
        val newDataList = mapDataList(request, data?.list, hasData)
        val canGetMore = newDataList?.canGetMore() == true
        val state = when (request) {
            is Request.Loading -> null
            is Request.Success -> if (canGetMore) PaginationState.READY else PaginationState.COMPLETE
            is Request.Error -> PaginationState.ERROR
        }
        return PaginationBundle(newDataList, state)
    }

    protected fun <T> mapMergePagination(
        request: Request<MergeList<T>>,
        data: MergePaginationBundle<T>?,
        sortType: SortType,
        isReload: Boolean
    ): MergePaginationBundle<T> {
        val newDataList = mapMergeList(request, data?.list, isReload)
        val canGetMore = newDataList?.canGetMore(sortType) == true
        val state = when (request) {
            is Request.Loading -> null
            is Request.Success -> if (canGetMore) PaginationState.READY else PaginationState.COMPLETE
            is Request.Error -> PaginationState.ERROR
        }
        return MergePaginationBundle(newDataList, state)
    }

    protected fun <T> mapRequestDefault(
        request: Request<T>,
        requestUi: RequestUi<T>,
        isSwr: Boolean = false
    ): RequestUi<T> {
        val hasDataBeforeRequest = requestUi.data != null
        val newData = mapData(request, requestUi.data)
        val hasDataAfterRequest = newData != null
        val newLoadState = mapLoadState(request, hasDataBeforeRequest, hasDataAfterRequest, isSwr)
        val newError = mapError(request, hasDataAfterRequest, requestUi.error)
        return RequestUi(newData, newLoadState, newError)
    }

    /**
     * Изменяет [RequestUi]
     * Если возвращается ошибка и если она [SpecificServerException] то она
     * передается в [errorSideEffects] иначе вызывается [ErrorHandler.handleError]
     */
    protected fun <T> mapRequestWithErrorHandlerPriority(
        request: Request<T>,
        requestUi: RequestUi<T>,
        isSwr: Boolean = false,
        errorSideEffects: (Throwable) -> Unit = {}
    ): RequestUi<T> {
        val hasDataBeforeRequest = requestUi.data != null
        val newData = mapData(request, requestUi.data)
        val hasDataAfterRequest = newData != null
        val newLoadState = mapLoadState(request, hasDataBeforeRequest, hasDataAfterRequest, isSwr)
        val newError = if (request is Request.Error) {
            request.error.also {
                if (it is SpecificServerException) {
                    errorSideEffects(it)
                } else {
                    handleErrorInMainThread(it)
                }
            }
        } else {
            requestUi.error
        }
        return RequestUi(newData, newLoadState, newError)
    }

    /**
     * Обрабатывает [Request]
     * Если возвращается ошибка и если она [SpecificServerException] то она
     * передается в [errorSideEffects] иначе вызывается [ErrorHandler.handleError]
     */
    protected fun <T> handleRequestErrorWithErrorHandler(
        request: Request<T>,
        errorSideEffects: (Throwable) -> Unit = {}
    ) {
        if (request is Request.Error) {
            request.error.let {
                if (it is SpecificServerException) {
                    errorSideEffects(it)
                } else {
                    handleErrorInMainThread(it)
                }
            }
        }
    }

    protected fun <T> mapPaginationDefault(
        request: Request<DataList<T>>,
        requestUi: RequestUi<PaginationBundle<T>>,
        isSwr: Boolean = false
    ): RequestUi<PaginationBundle<T>> {
        val hasDataBeforeRequest = requestUi.data?.hasData ?: false
        val newData = mapPagination(request, requestUi.data)
        val hasDataAfterRequest = newData.hasData
        val newLoadState =
            mapLoadState(request, hasDataBeforeRequest, hasDataAfterRequest, isSwr)
        val newError = mapError(request, hasDataAfterRequest, requestUi.error)
        return RequestUi(newData, newLoadState, newError)
    }

    protected fun <T> mapMergePaginationDefault(
        request: Request<MergeList<T>>,
        requestUi: RequestUi<MergePaginationBundle<T>>,
        sortType: SortType = SortType.RECENT_FIRST,
        isSwr: Boolean = false
    ): RequestUi<MergePaginationBundle<T>> {
        val hasDataBeforeRequest = requestUi.data?.hasData ?: false
        val newData = mapMergePagination(request, requestUi.data, sortType, isSwr)
        val hasDataAfterRequest = newData.hasData
        val newLoadState =
            mapLoadState(request, hasDataBeforeRequest, hasDataAfterRequest, isSwr)
        val newError = mapError(request, hasDataAfterRequest, requestUi.error)
        return RequestUi(newData, newLoadState, newError)
    }

    protected fun handleErrorInMainThread(error: Throwable) {
        MainThreadImmediateScheduler.scheduleDirect { errorHandler.handleError(error) }
    }

    protected fun <T> State<T>.emitNewState(transformer: T.() -> T) {
        val newState = transformer(value)
        accept(newState)
    }
}