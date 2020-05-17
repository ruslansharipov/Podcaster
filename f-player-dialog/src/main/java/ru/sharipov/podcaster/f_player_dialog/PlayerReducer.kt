package ru.sharipov.podcaster.f_player_dialog

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import javax.inject.Inject

data class PlayerState(
    val input: String = EMPTY_STRING
)

@PerScreen
class PlayerStateHolder @Inject constructor() : State<PlayerState>(PlayerState())

@PerScreen
class PlayerReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: PlayerStateHolder
) : StateReducer(dependency)