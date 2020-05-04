package ru.sharipov.podcaster.base_feature.ui.service.ping

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Single
import ru.surfstudio.android.dagger.scope.PerApplication
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerApplication
class PingBus @Inject constructor() {

    private val pingRelay = PublishRelay.create<Unit>()
    private val isStartedRelay = PublishRelay.create<Boolean>()

    private val falseTimer = Observable
        .timer(800, TimeUnit.MILLISECONDS)
        .map { false }

    fun pingAndListen(): Single<Boolean> {
        pingRelay.accept(Unit)
        return Observable
            .merge(falseTimer, isStartedRelay.hide())
            .firstOrError()
    }

    fun pong() {
        isStartedRelay.accept(true)
    }

    fun observePing(): Observable<Unit> {
        return pingRelay.hide()
    }
}