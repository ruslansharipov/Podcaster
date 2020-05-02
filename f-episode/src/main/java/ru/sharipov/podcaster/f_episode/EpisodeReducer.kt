package ru.sharipov.podcaster.f_episode

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

class EpisodeState {

}

@PerScreen
class EpisodeStateHolder @Inject constructor(): State<EpisodeState>(EpisodeState()) {

}

@PerScreen
class EpisodeReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: EpisodeStateHolder
): StateReducer(dependency) {

}
