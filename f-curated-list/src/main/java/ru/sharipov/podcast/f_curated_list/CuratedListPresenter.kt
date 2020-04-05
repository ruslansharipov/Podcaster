package ru.sharipov.podcast.f_curated_list

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class CuratedListPresenter @Inject constructor(
    dependency: StatePresenterDependency
) : StatePresenter(dependency) {

}
