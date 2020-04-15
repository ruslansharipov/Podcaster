package ru.sharipov.podcaster.f_podcast

import ru.sharipov.podcaster.base.datalist_date.MergeList
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.pagination.MergePaginationBundle
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.PodcastFull
import ru.sharipov.podcaster.domain.SortType
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class PodcastState(
    val podcast: PodcastFull,
    val sortType: SortType = SortType.RECENT_FIRST,
    val isSubscribed: Boolean = false,
    val episodes: RequestUi<MergePaginationBundle<Episode>> = RequestUi(),
    val details: RequestUi<PodcastFull> = RequestUi()
)

@PerScreen
class PodcastStateHolder @Inject constructor(route: PodcastFragmentRoute) :
    State<PodcastState>(PodcastState(route.podcast))

@PerScreen
class PodcastReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: PodcastStateHolder
) : StateReducer(dependency) {

    fun onEpisodesLoad(request: Request<MergeList<Episode>>) {
        sh.emitNewState {
            copy(
                episodes = mapMergePaginationDefault(request, episodes)
            )
        }
    }

    fun onDetailsLoad(request: Request<PodcastFull>) {
        sh.emitNewState {
            copy(
                details = mapRequestDefault(request, details)
            )
        }
    }
}
