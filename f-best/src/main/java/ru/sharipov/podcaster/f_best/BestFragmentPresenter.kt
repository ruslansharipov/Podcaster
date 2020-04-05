package ru.sharipov.podcaster.f_best

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class BestFragmentPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: BestReducer,
    private val podcastInteractor: PodcastInteractor
) : StatePresenter(dependency) {

    override fun onFirstLoad() {

    }

}
