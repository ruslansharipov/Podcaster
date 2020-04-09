package ru.sharipov.podcaster.i_genres

import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Region
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class RegionsInteractor @Inject constructor(
    private val regionStorage: RegionStorage,
    private val regionsRepository: RegionsRepository
) {

    var region: Region
        get() = regionStorage.currentRegion
        set(value) {
            regionStorage.currentRegion = value
        }

    fun observeRegionChanges(): Observable<Region> {
        return regionStorage.observeRegionChanges()
    }

    fun getRegions(): Observable<List<Region>> {
        return regionsRepository.getRegions()
    }

}