package ru.sharipov.podcaster.base_feature.ui.base.presenter

import androidx.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.internal.observers.LambdaObserver
import ru.surfstudio.android.core.mvi.ui.relation.StateEmitter
import ru.surfstudio.android.core.mvp.binding.rx.builders.RxBuilderIo
import ru.surfstudio.android.core.ui.event.lifecycle.completely.destroy.OnCompletelyDestroyDelegate
import ru.surfstudio.android.core.ui.event.lifecycle.ready.OnViewReadyDelegate
import ru.surfstudio.android.core.ui.state.ScreenState
import ru.surfstudio.android.rx.extension.ObservableUtil
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider

abstract class StatePresenter(
    dependency: StatePresenterDependency
) : LifecyclePresenter(),
    StateEmitter,
    RxBuilderIo {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val screenState: ScreenState = dependency.screenState
    override val schedulersProvider: SchedulersProvider = dependency.schedulersProvider

    init {
        dependency.eventDelegateManager.registerDelegate(StatePresenterGateway(this, screenState))
    }

    fun <T> Observable<T>.subscribeIoWithFreeze(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = {}
    ): Disposable = subscribeWithFreeze(onNext, onError)

    fun <T> Observable<T>.subscribeWithFreeze(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = {}
    ): Disposable {
        val observer = LambdaObserver<T>(
            Consumer(onNext),
            Consumer(onError),
            ObservableUtil.EMPTY_ACTION,
            Functions.emptyConsumer()
        )

        val disposable = this.subscribeWith(observer)

        disposables.add(disposable)
        return disposable
    }

    @CallSuper
    override fun onCompletelyDestroy() {
        disposables.dispose()
    }
}