package ru.sharipov.podcaster.f_profile

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class ActivityState(
    val historyItems: List<Episode> = emptyList()
)

@PerScreen
class ActivityStateHolder @Inject constructor() : State<ActivityState>(ActivityState())

@PerScreen
class ActivityReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: ActivityStateHolder
) : StateReducer(dependency) {

    fun historyChanged(newItems: List<Episode>){
        sh.emitNewState {
            copy(historyItems = newItems)
        }
    }

}