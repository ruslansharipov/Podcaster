package ru.sharipov.podcaster.base_feature.ui.bus

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.data.SystemBarsSize
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class InsetsInteractor @Inject constructor() {

    private val insetsRelay = PublishRelay.create<SystemBarsSize>()

    fun observeInsets(): Observable<SystemBarsSize> {
        return insetsRelay.hide()
    }

    fun emitInsets(insets: SystemBarsSize) {
        insetsRelay.accept(insets)
    }
}