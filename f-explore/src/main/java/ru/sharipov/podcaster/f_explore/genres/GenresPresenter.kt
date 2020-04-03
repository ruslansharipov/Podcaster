package ru.sharipov.podcaster.f_explore.genres

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class GenresPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: GenresReducer
) : StatePresenter(dependency) {

    override fun onFirstLoad() {

    }
}