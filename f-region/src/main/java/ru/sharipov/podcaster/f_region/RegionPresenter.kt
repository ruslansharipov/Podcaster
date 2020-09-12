package ru.sharipov.podcaster.f_region

import com.jakewharton.rxrelay2.PublishRelay
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.dismiss
import ru.sharipov.podcaster.base_feature.ui.navigation.RegionDialogRoute
import ru.sharipov.podcaster.domain.Region
import ru.sharipov.podcaster.i_genres.RegionsInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.NavigationCommandExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerScreen
class RegionPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val route: RegionDialogRoute,
    private val reducer: RegionReducer,
    private val regionsInteractor: RegionsInteractor,
    private val navigationExecutor: NavigationCommandExecutor
) : StatePresenter(dependency) {

    companion object {
        private const val DEBOUNCE_MS: Long = 300
    }

    private val inputRelay = PublishRelay.create<String>()

    override fun onFirstLoad() {
        reducer.onCurrentRegionLoaded(regionsInteractor.region)
        loadAvailableRegions()
        subscribeOnInputChanges()
    }

    private fun subscribeOnInputChanges() {
        inputRelay
            .debounce(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
            .subscribeDefault(reducer::onInputDebounced)
    }

    fun onRegionSelected(selectedRegion: Region) {
        regionsInteractor.region = selectedRegion
        dismissRegionDialog()
    }

    fun onInputChange(newInput: String) {
        inputRelay.accept(newInput)
    }

    fun onErrorClick() {
        loadAvailableRegions()
    }

    fun onBackClick() {
        dismissRegionDialog()
    }

    fun onClearClick() {
        reducer.onClearClick()
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

    private fun dismissRegionDialog() {
        navigationExecutor.dismiss(route)
    }
}
