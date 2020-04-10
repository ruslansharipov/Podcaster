package ru.sharipov.podcaster.f_region

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.RegionDialogRoute
import ru.sharipov.podcaster.domain.Region
import ru.sharipov.podcaster.i_genres.RegionsInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigator
import javax.inject.Inject

@PerScreen
class RegionPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val route: RegionDialogRoute,
    private val reducer: RegionReducer,
    private val regionsInteractor: RegionsInteractor,
    private val dialogNavigator: DialogNavigator
) : StatePresenter(dependency) {

    override fun onFirstLoad() {
        reducer.onCurrentRegionLoaded(regionsInteractor.region)
        loadAvailableRegions()
    }

    fun onRegionSelected(selectedRegion: Region) {
        regionsInteractor.region = selectedRegion
        dialogNavigator.dismiss(route)
    }

    private fun loadAvailableRegions() {
        regionsInteractor.getRegions()
            .io()
            .map { regions: List<Region> ->
                val currentRegion = regionsInteractor.region
                regions.map { region: Region ->
                    SelectableRegion(
                        region = region,
                        isSelected = region == currentRegion
                    )
                }
            }
            .asRequest()
            .subscribeDefault(reducer::onRegionsLoaded)
    }
}
