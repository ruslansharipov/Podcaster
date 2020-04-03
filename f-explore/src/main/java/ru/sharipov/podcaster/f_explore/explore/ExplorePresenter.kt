package ru.sharipov.podcaster.f_explore.explore

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class ExplorePresenter @Inject constructor(
    private val exploreReducer: ExploreReducer,
    basePresenterDependency: StatePresenterDependency
) : StatePresenter(basePresenterDependency) {

}
