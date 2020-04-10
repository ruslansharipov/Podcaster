package ru.sharipov.podcaster.f_region

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.domain.Region
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class RegionState(
    val currentRegion: Region = Region(),
    val regions: RequestUi<List<SelectableRegion>> = RequestUi()
)

@PerScreen
class RegionStateHolder @Inject constructor() : State<RegionState>(RegionState())

@PerScreen
class RegionReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: RegionStateHolder
) : StateReducer(dependency) {

    fun onCurrentRegionLoaded(newRegion: Region) {
        sh.emitNewState {
            copy(currentRegion = newRegion)
        }
    }

    fun onRegionsLoaded(request: Request<List<SelectableRegion>>) {
        sh.emitNewState {
            copy(
                regions = mapRequestDefault(request, regions)
            )
        }
    }

}