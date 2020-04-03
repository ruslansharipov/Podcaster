package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class MainState(
    val currentTabType: MainTabType = MainTabType.EXPLORE
)

@PerScreen
class MainStateHolder @Inject constructor() : State<MainState>(MainState())

@PerScreen
class MainReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val stateHolder: MainStateHolder
) : StateReducer(dependency) {

    fun onTabSelected(tabType: MainTabType) {
        stateHolder.emitNewState {
            copy(currentTabType = tabType)
        }
    }

}