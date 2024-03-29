package ru.sharipov.podcaster.f_best

import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.getNextPage
import ru.sharipov.podcaster.base_feature.ui.extesions.removeLastFragment
import ru.sharipov.podcaster.base_feature.ui.extesions.replace
import ru.sharipov.podcaster.base_feature.ui.extesions.show
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.RegionDialogRoute
import ru.sharipov.podcaster.domain.PodcastFull
import ru.sharipov.podcaster.domain.Region
import ru.sharipov.podcaster.i_genres.RegionsInteractor
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

@PerScreen
class BestFragmentPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val sh: BestStateHolder,
    private val reducer: BestReducer,
    private val podcastInteractor: PodcastInteractor,
    private val regionsInteractor: RegionsInteractor,
    private val navigationExecutor: AppCommandExecutor
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
        navigationExecutor.show(RegionDialogRoute())
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
                reducer.onPodcastsLoaded(request, isSwr = true)
            }
    }

    private fun createPodcastsObservable(
        nextPage: Int,
        region: String
    ): Observable<Request<DataList<PodcastFull>>> {
        return podcastInteractor
            .getBestPodcasts(nextPage, region, sh.value.genre.id)
            .io()
            .asRequest()
    }

    fun onPodcastClick(podcast: PodcastFull) {
        navigationExecutor.replace(PodcastFragmentRoute(podcast))
    }

    fun onBackClick() {
        navigationExecutor.removeLastFragment()
    }

}
