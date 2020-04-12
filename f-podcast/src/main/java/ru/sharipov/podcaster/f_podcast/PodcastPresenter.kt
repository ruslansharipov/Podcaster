package ru.sharipov.podcaster.f_podcast

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class PodcastPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: PodcastReducer,
    private val sh: PodcastStateHolder
) : StatePresenter(dependency) {

}
