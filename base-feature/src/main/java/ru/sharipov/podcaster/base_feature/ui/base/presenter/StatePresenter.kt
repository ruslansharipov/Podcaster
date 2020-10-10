package ru.sharipov.podcaster.base_feature.ui.base.presenter

import androidx.annotation.CallSuper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import ru.surfstudio.android.core.mvi.ui.relation.StateEmitter
import ru.surfstudio.android.core.mvp.binding.rx.builders.RxBuilderIo
import ru.surfstudio.android.core.ui.state.ScreenState
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

    fun <T> Observable<T>.subscribeIoDefault(onNext: (T) -> Unit): Disposable =
        io().subscribeDefault(onNext)

    fun <T> Observable<T>.subscribeDefault(onNext: (T) -> Unit): Disposable {
        val disposable = this
            .main()
            .subscribe(Consumer(onNext), Consumer {})

        disposables.add(disposable)
        return disposable
    }

    fun <T> Single<T>.subscribeDefault(onNext: (T) -> Unit): Disposable {
        return this.toObservable().subscribeDefault(onNext)
    }

    fun <T> Flowable<T>.subscribeIoDefault(onNext: (T) -> Unit): Disposable {
        return this
            .subscribeOn(schedulersProvider.worker())
            .subscribeDefault(onNext)
    }

    fun <T> Flowable<T>.subscribeDefault(onNext: (T) -> Unit): Disposable {
        val disposable = this
            .observeOn(schedulersProvider.main())
            .subscribe(Consumer(onNext), Consumer {})

        disposables.add(disposable)
        return disposable
    }

    fun Completable.subscribeDefault(onComplete: () -> Unit = {}): Disposable {
        val disposable = this
            .observeOn(schedulersProvider.main())
            .subscribe({ onComplete() }, {  })

        disposables.add(disposable)
        return disposable
    }

    fun Completable.subscribeIoDefault(onComplete: () -> Unit = {}): Disposable {
        return this.io().subscribeDefault(onComplete)
    }

    @CallSuper
    override fun onCompletelyDestroy() {
        disposables.dispose()
    }
}