package ru.sharipov.podcaster.f_podcast

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.domain.SortType
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class PodcastState(
    val id: String,
    val sortType: SortType = SortType.RECENT_FIRST
)

@PerScreen
class PodcastStateHolder @Inject constructor(route: PodcastFragmentRoute) :
    State<PodcastState>(PodcastState(route.id))

@PerScreen
class PodcastReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: PodcastStateHolder
) : StateReducer(dependency)
