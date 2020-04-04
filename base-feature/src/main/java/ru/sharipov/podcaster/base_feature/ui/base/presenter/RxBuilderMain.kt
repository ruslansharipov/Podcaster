package ru.sharipov.podcaster.base_feature.ui.base.presenter

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider

interface RxBuilderMain {

    val schedulersProvider: SchedulersProvider

    /**
     * Build-функция, переводящая [Single] в главный поток
     */
    fun <T> Single<T>.main(): Single<T> =
        observeOn(schedulersProvider.main())

    /**
     * Build-функция, переводящая [Observable] в главный поток
     */
    fun <T> Observable<T>.main(): Observable<T> =
        observeOn(schedulersProvider.main())

    /**
     * Build-функция, переводящая [Observable] в главный поток
     */
    fun <T> Maybe<T>.main(): Maybe<T> =
        observeOn(schedulersProvider.main())

    /**
     * Build-функция, переводящая [Completable] в главный поток
     */
    fun Completable.main(): Completable =
        observeOn(schedulersProvider.main())
}