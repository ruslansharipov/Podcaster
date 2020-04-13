package ru.sharipov.podcaster.f_podcast

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class PodcastPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: PodcastReducer,
    private val sh: PodcastStateHolder,
    private val tabNavigator: TabFragmentNavigator
) : StatePresenter(dependency) {

    fun onBackClick() {
        tabNavigator.onBackPressed()
    }

}
