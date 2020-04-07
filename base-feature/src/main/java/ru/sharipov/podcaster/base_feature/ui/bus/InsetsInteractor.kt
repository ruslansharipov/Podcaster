package ru.sharipov.podcaster.base_feature.ui.bus

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.data.AppInsets
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class InsetsInteractor @Inject constructor() {

    private val insetsRelay = PublishRelay.create<AppInsets>()

    fun observeInsets(): Observable<AppInsets> {
        return insetsRelay.hide()
    }

    fun emitInsets(insets: AppInsets) {
        insetsRelay.accept(insets)
    }
}