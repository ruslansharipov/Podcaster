package ru.sharipov.podcaster.f_podcast

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.SortType
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class PodcastPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: PodcastReducer,
    private val sh: PodcastStateHolder,
    private val tabNavigator: TabFragmentNavigator,
    private val podcastInteractor: PodcastInteractor
) : StatePresenter(dependency) {

    override fun onFirstLoad() {
        loadEpisodes()
        loadDetails()
    }

    fun onSwipeRefresh() {
        loadEpisodes(isSwr = true)
        loadDetails()
    }

    fun onErrorClick() {
        loadEpisodes()
        loadDetails()
    }

    fun onShowMore() {
        loadEpisodes(sh.value.episodes.data?.list?.nextEpisodePubDate)
    }

    fun onEpisodeClick(episode: Episode) {

    }

    fun onBackClick() {
        tabNavigator.onBackPressed()
    }

    private fun loadDetails() {
        val podcast = sh.value.podcast
        if (podcast.isShort) {
            podcastInteractor
                .getPodcast(podcast.id)
                .io()
                .asRequest()
                .subscribeDefault(reducer::onDetailsLoad)
        } else {
            reducer.onDetailsLoad(Request.Success(podcast))
        }
    }

    private fun loadEpisodes(nextDate: Long? = null, isSwr: Boolean = false) {
        podcastInteractor
            .getPodcastEpisodes(sh.value.podcast.id, SortType.RECENT_FIRST, nextDate)
            .io()
            .asRequest()
            .subscribeDefault { reducer.onEpisodesLoad(it, isSwr) }
    }

}
