package ru.sharipov.podcaster.f_podcast_episodes

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class EpisodesPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: EpisodesReducer,
    private val sh: EpisodesStateHolder
) : StatePresenter(dependency) {

}
