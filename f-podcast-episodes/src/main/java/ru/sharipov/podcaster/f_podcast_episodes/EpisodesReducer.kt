package ru.sharipov.podcaster.f_podcast_episodes

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodesFragmentRoute
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class EpisodesState(
    val id: String
)

@PerScreen
class EpisodesStateHolder @Inject constructor(route: EpisodesFragmentRoute) :
    State<EpisodesState>(EpisodesState(route.id))

@PerScreen
class EpisodesReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: EpisodesStateHolder
) : StateReducer(dependency) {

}
