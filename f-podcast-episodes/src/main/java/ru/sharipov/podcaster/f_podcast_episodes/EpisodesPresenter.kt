package ru.sharipov.podcaster.f_podcast_episodes

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.SortType
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class EpisodesPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: EpisodesReducer,
    private val sh: EpisodesStateHolder,
    private val podcastInteractor: PodcastInteractor
) : StatePresenter(dependency) {

    override fun onFirstLoad() {
        loadEpisodes()
    }

    private fun loadEpisodes(nextDate: Long? = null) {
        podcastInteractor
            .getPodcastEpisodes(sh.value.id, SortType.RECENT_FIRST, nextDate)
            .io()
            .asRequest()
            .subscribeDefault(reducer::onEpisodesLoad)
    }

    fun onErrorClick() {

    }

    fun onShowMore() {
        loadEpisodes(sh.value.episodes.data?.list?.nextEpisodePubDate)
    }

    fun onEpisodeClick(episode: Episode) {

    }

}
