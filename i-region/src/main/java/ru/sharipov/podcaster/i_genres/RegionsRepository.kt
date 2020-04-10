package ru.sharipov.podcaster.i_genres

import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Region
import ru.sharipov.podcaster.i_network.network.transform
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class RegionsRepository @Inject constructor(
    private val regionApi: RegionApi
) {

    fun getRegions(): Observable<List<Region>> {
        return regionApi
            .getRegions()
            .transform()
            .map { it.sortedBy(Region::name) }
            .toObservable()
    }
}