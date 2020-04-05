package ru.sharipov.podcast.f_curated_list

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class CuratedListPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val sh: CuratedListStateHolder,
    private val reducer: CuratedListReducer,
    private val podcastInteractor: PodcastInteractor
) : StatePresenter(dependency) {

    override fun onFirstLoad() {
        loadCuratedPodcasts(0)
    }

    fun loadMore() {
        val nextPage = sh.value.curatedItems.data?.list?.nextPage ?: 0
        loadCuratedPodcasts(nextPage)
    }

    private fun loadCuratedPodcasts(nextPage: Int) {
        podcastInteractor.getCuratedPodcasts(nextPage)
            .asRequest()
            .subscribeIoDefault(reducer::onCuratedLoaded)
    }

}
