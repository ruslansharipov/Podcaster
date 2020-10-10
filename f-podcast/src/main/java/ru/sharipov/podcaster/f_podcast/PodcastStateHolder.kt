package ru.sharipov.podcaster.f_podcast

import ru.sharipov.podcaster.base.datalist_date.MergeList
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.pagination.MergePaginationBundle
import ru.sharipov.podcaster.domain.*
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class PodcastState(
    val podcast: Subscription,
    val sortType: SortType = SortType.RECENT_FIRST,
    val episodes: RequestUi<MergePaginationBundle<Episode>> = RequestUi(),
    val details: RequestUi<Subscription> = RequestUi(),
    val isSubscribed: Boolean = false
) {
    val description: String = if (podcast is PodcastFull) podcast.description else EMPTY_STRING
    val totalEpisodes: Int? = if (podcast is PodcastFull && !podcast.isShort) podcast.totalEpisodes else null
}

@PerScreen
class PodcastStateHolder @Inject constructor(route: PodcastFragmentRoute) :
    State<PodcastState>(PodcastState(route.podcast))

@PerScreen
class PodcastReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: PodcastStateHolder
) : StateReducer(dependency) {

    fun onEpisodesLoad(request: Request<MergeList<Episode>>, isSwr: Boolean) {
        sh.emitNewState {
            copy(
                episodes = mapMergePaginationDefault(request, episodes, isSwr)
            )
        }
    }

    fun onDetailsLoad(request: Request<Subscription>) {
        sh.emitNewState {
            copy(
                details = mapRequestDefault(request, details),
                podcast = if (request is Request.Success) request.data else podcast
            )
        }
    }

    fun onSubscriptionChange(isSubscribed: Boolean){
        sh.emitNewState {
            copy(
                isSubscribed = isSubscribed
            )
        }
    }
}
