package ru.sharipov.podcaster.f_best

import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.getNextPage
import ru.sharipov.podcaster.base_feature.ui.navigation.RegionDialogRoute
import ru.sharipov.podcaster.domain.Podcast
import ru.sharipov.podcaster.domain.Region
import ru.sharipov.podcaster.i_genres.RegionsInteractor
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigator
import javax.inject.Inject

@PerScreen
class BestFragmentPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val sh: BestStateHolder,
    private val reducer: BestReducer,
    private val podcastInteractor: PodcastInteractor,
    private val regionsInteractor: RegionsInteractor,
    private val tabNavigator: TabFragmentNavigator,
    private val dialogNavigator: DialogNavigator
) : StatePresenter(dependency) {

    companion object {
        const val INITIAL_PAGE = 1
    }

    override fun onFirstLoad() {
        subscribeOnRegionChanges()
        loadPodcasts(INITIAL_PAGE)
    }

    fun onErrorClick() {
        loadPodcasts()
    }

    fun onRefresh() {
        loadPodcasts(INITIAL_PAGE, true)
    }

    fun loadMore() {
        loadPodcasts()
    }

    fun onRegionClick() {
        dialogNavigator.show(RegionDialogRoute())
    }

    private fun loadPodcasts(
        nextPage: Int = sh.value.podcasts.getNextPage(),
        isSwr: Boolean = false
    ) {
        createPodcastsObservable(
            nextPage = nextPage,
            region = sh.value.region.code
        ).subscribeDefault { reducer.onPodcastsLoaded(it, isSwr) }
    }

    private fun subscribeOnRegionChanges() {
        regionsInteractor
            .observeRegionChanges()
            .doOnNext(reducer::onRegionChanged)
            .flatMap { newRegion: Region ->
                createPodcastsObservable(
                    nextPage = INITIAL_PAGE,
                    region = newRegion.code
                )
            }.subscribeDefault{ request ->
                reducer.onPodcastsLoaded(request, isSwr = false)
            }
    }

    private fun createPodcastsObservable(
        nextPage: Int,
        region: String
    ): Observable<Request<DataList<Podcast>>> {
        return podcastInteractor
            .getBestPodcasts(nextPage, region, sh.value.genre.id)
            .io()
            .asRequest()
    }

    fun onPodcastClick(podcast: Podcast) {
        // todo
    }

    fun onBackClick() {
        tabNavigator.onBackPressed()
    }

}
