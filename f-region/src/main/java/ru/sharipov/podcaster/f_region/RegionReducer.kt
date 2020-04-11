package ru.sharipov.podcaster.f_region

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.placeholderState
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderState
import ru.sharipov.podcaster.domain.Region
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import javax.inject.Inject

data class RegionState(
    val input: String = EMPTY_STRING,
    val currentRegion: Region = Region(),
    val regions: RequestUi<List<SelectableRegion>> = RequestUi(),
    val regionsList: List<SelectableRegion> = emptyList()
) {
    val isClearBtnVisible: Boolean get() = input.isNotEmpty()
    val isInputEnabled: Boolean get() = regions.data != null
    val placeholderState: PlaceholderState
        get() = when {
            regions.data != null && regionsList.isEmpty() -> PlaceholderState.Empty
            else -> regions.placeholderState
        }
}

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
                regions = mapRequestDefault(request, regions),
                regionsList = if (request is Request.Success) request.data else regionsList
            )
        }
    }

    fun onClearClick() {
        sh.emitNewState {
            copy(
                input = EMPTY_STRING,
                regionsList = regions.data ?: emptyList()
            )
        }
    }

    fun onInputDebounced(newInput: String) {
        sh.emitNewState {
            copy(
                input = newInput,
                regionsList = regions
                    .data
                    ?.filter { it.region.name.contains(newInput, ignoreCase = true) }
                    ?: emptyList()
            )
        }
    }

}