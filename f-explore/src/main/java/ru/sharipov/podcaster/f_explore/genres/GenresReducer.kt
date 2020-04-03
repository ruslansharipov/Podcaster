package ru.sharipov.podcaster.f_explore.genres

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen

class GenresState

@PerScreen
class GenresStateHolder : State<GenresState>(GenresState())

@PerScreen
class GenresReducer(
    dependency: StateReducerDependency,
    private val sh: GenresStateHolder
) : StateReducer(dependency) {

}