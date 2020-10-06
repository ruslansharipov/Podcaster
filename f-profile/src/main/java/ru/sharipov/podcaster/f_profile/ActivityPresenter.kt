package ru.sharipov.podcaster.f_profile

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.show
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeFragmentRoute
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.i_history.HistoryInteractor
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

@PerScreen
class ActivityPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: ActivityReducer,
    private val navigationExecutor: AppCommandExecutor,
    private val historyInteractor: HistoryInteractor
) : StatePresenter(dependency) {

    init {
        historyInteractor
            .observeHistory()
            .subscribeIoDefault(reducer::historyChanged)
    }

    fun onSettingsClick() {
        // TODO navigate to settings
    }

    fun onEpisodeClick(episode: Episode) {
        navigationExecutor.show(EpisodeFragmentRoute(episode))
    }
}