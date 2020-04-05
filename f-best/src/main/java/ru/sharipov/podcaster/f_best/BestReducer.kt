package ru.sharipov.podcaster.f_best

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.surfstudio.android.core.mvp.binding.rx.extensions.Optional
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

class BestState(
    val categoryUi: Optional<String> = Optional.empty(),
    val regionUi: Optional<String> = Optional.empty()
)

@PerScreen
class BestStateHolder @Inject constructor() : State<BestState>(BestState())

@PerScreen
class BestReducer @Inject constructor(dependency: StateReducerDependency) :
    StateReducer(dependency) {


}
