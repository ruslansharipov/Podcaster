package ru.sharipov.podcaster.f_best

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class BestFragmentPresenter @Inject constructor(
    dependency: StatePresenterDependency
): StatePresenter(dependency) {

}
