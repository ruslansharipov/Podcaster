package ru.sharipov.podcast.f_curated_list

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

class CuratedListState

@PerScreen
class CuratedListStateHolder @Inject constructor() : State<CuratedListState>(CuratedListState())

@PerScreen
class CuratedListReducer @Inject constructor(dependency: StateReducerDependency) :
    StateReducer(dependency) {


}