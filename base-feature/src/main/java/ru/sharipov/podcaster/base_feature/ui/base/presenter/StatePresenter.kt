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
import ru.surfstudio.android.core.ui.state.ScreenState
import ru.surfstudio.android.rx.extension.ObservableUtil
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider

abstract class StatePresenter(
    dependency: StatePresenterDependency
) : LifecyclePresenter(),
    StateEmitter,
    RxBuilderIo,
    RxBuilderMain {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val screenState: ScreenState = dependency.screenState
    override val schedulersProvider: SchedulersProvider = dependency.schedulersProvider

    init {
        dependency.eventDelegateManager.registerDelegate(StatePresenterGateway(this, screenState))
    }

    fun <T> Observable<T>.subscribeIoDefault(onNext: (T) -> Unit): Disposable = io().subscribeDefault(onNext)

    fun <T> Observable<T>.subscribeDefault(onNext: (T) -> Unit): Disposable {
        val observer = LambdaObserver<T>(
            Consumer(onNext),
            Consumer {},
            ObservableUtil.EMPTY_ACTION,
            Functions.emptyConsumer()
        )

        val disposable = this
            .observeOn(schedulersProvider.main())
            .subscribeWith(observer)

        disposables.add(disposable)
        return disposable
    }

    @CallSuper
    override fun onCompletelyDestroy() {
        disposables.dispose()
    }
}