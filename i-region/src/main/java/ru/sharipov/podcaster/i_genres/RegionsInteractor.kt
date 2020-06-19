package ru.sharipov.podcaster.i_genres

import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Region
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Interactor for getting available regions and storing user's region
 */
@PerApplication
class RegionsInteractor @Inject constructor(
    private val regionStorage: RegionStorage,
    private val regionsRepository: RegionsRepository
) {

    /**
     * User's region
     */
    var region: Region
        get() = regionStorage.currentRegion
        set(value) {
            regionStorage.currentRegion = value
        }

    /** [RegionStorage.observeRegionChanges] */
    fun observeRegionChanges(): Observable<Region> {
        return regionStorage.observeRegionChanges()
    }

    /**
     * Get available regions
     */
    fun getRegions(): Observable<List<Region>> {
        //TODO добавить кэш
        return regionsRepository.getRegions()
    }

}