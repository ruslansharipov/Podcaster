package ru.sharipov.podcaster.f_explore.explore

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

class ExploreState()

@PerScreen
class ExploreStateHolder @Inject constructor() : State<ExploreState>(ExploreState())

@PerScreen
class ExploreReducer @Inject constructor(
    private val sh: ExploreStateHolder,
    dependency: StateReducerDependency
) : StateReducer(dependency) {

}