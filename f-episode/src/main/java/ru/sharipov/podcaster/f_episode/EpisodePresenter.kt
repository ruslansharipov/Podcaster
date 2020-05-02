package ru.sharipov.podcaster.f_episode

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class EpisodePresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val episodeReducer: EpisodeReducer,
    private val tabNavigator: TabFragmentNavigator
): StatePresenter(dependency) {

    fun onBackPressed() {
        tabNavigator.onBackPressed()
    }

}
