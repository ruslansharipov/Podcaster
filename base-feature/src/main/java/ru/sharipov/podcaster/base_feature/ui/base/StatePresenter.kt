package ru.sharipov.podcaster.base_feature.ui.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.internal.observers.LambdaObserver
import ru.surfstudio.android.core.mvp.binding.rx.builders.RxBuilderIo
import ru.surfstudio.android.core.ui.event.lifecycle.completely.destroy.OnCompletelyDestroyDelegate
import ru.surfstudio.android.rx.extension.ObservableUtil
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider

abstract class StatePresenter(
    dependency: StatePresenterDependency
): OnCompletelyDestroyDelegate, RxBuilderIo {

    private val disposables : CompositeDisposable = CompositeDisposable()

    override val schedulersProvider: SchedulersProvider = dependency.schedulersProvider

    init {
        dependency.eventDelegateManager.registerDelegate(this)
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

    override fun onCompletelyDestroy() {
        disposables.dispose()
    }
}