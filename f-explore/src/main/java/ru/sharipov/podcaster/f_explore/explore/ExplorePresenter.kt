package ru.sharipov.podcaster.f_explore.explore

import ru.sharipov.podcaster.base_feature.ui.base.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.StatePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class ExplorePresenter @Inject constructor(
    basePresenterDependency: StatePresenterDependency
) : StatePresenter(basePresenterDependency) {

}
