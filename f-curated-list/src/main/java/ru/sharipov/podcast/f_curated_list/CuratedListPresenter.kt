package ru.sharipov.podcast.f_curated_list

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.replace
import ru.sharipov.podcaster.base_feature.ui.extesions.show
import ru.sharipov.podcaster.base_feature.ui.navigation.BestFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.SearchDialogRoute
import ru.sharipov.podcaster.domain.CuratedItem
import ru.sharipov.podcaster.domain.PodcastShort
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.sharipov.podcaster.i_search.SearchInteractor
import ru.sharipov.podcaster.i_search.SearchResult
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.NavigationCommandExecutor
import javax.inject.Inject

@PerScreen
class CuratedListPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val sh: CuratedListStateHolder,
    private val reducer: CuratedListReducer,
    private val searchInteractor: SearchInteractor,
    private val podcastInteractor: PodcastInteractor,
    private val navigationExecutor: NavigationCommandExecutor
) : StatePresenter(dependency) {

    private companion object {
        const val INITIAL_PAGE = 1
    }

    override fun onFirstLoad() {
        subscribeOnSearchResults()
        loadCuratedPodcasts(INITIAL_PAGE) //TODO раскомментить в релизной версии=)
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
        navigationExecutor.replace(PodcastFragmentRoute(podcast.toPodcastFull()))
    }

    fun onSearchClick() {
        navigationExecutor.show(SearchDialogRoute())
    }

    private fun loadCuratedPodcasts(nextPage: Int, isSwr: Boolean = false) {
        podcastInteractor.getCuratedPodcasts(nextPage)
            .asRequest()
            .subscribeIoDefault { reducer.onCuratedLoaded(it, isSwr) }
    }

    private fun subscribeOnSearchResults(){
        searchInteractor
            .observeSearchResult()
            .subscribeDefault { result ->
                val route = when(result){
                    is SearchResult.GenreResult -> BestFragmentRoute(result.genre)
                    is SearchResult.PodcastResult -> PodcastFragmentRoute(result.podcast)
                }
                navigationExecutor.replace(route)
            }
    }

}
