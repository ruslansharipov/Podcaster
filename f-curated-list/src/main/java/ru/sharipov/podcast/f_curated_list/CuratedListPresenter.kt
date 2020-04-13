package ru.sharipov.podcast.f_curated_list

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.domain.CuratedItem
import ru.sharipov.podcaster.domain.PodcastShort
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class CuratedListPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val sh: CuratedListStateHolder,
    private val reducer: CuratedListReducer,
    private val podcastInteractor: PodcastInteractor,
    private val tabNavigator: TabFragmentNavigator
) : StatePresenter(dependency) {

    private companion object {
        const val INITIAL_PAGE = 1
    }

    override fun onFirstLoad() {
        //loadCuratedPodcasts(INITIAL_PAGE) //TODO раскомментить в релизной версии=)
    }

    fun loadMore() {
        val nextPage = sh.value.curatedItems.data?.list?.nextPage ?: 0
        loadCuratedPodcasts(nextPage)
    }

    fun reload() {
        loadCuratedPodcasts(INITIAL_PAGE, true)
    }

    fun allClick(item: CuratedItem) {

    }

    fun podcastClick(podcast: PodcastShort) {
        tabNavigator.open(PodcastFragmentRoute(podcast.toFull()))
    }

    private fun loadCuratedPodcasts(nextPage: Int, isSwr: Boolean = false) {
        podcastInteractor.getCuratedPodcasts(nextPage)
            .asRequest()
            .subscribeIoDefault { reducer.onCuratedLoaded(it, isSwr) }
    }

}
