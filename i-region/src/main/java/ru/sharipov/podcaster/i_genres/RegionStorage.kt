package ru.sharipov.podcaster.i_genres

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Region
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage

/**
 * User's region storage
 */
class RegionStorage(
    cacheDir: String,
    namingProcessor: NamingProcessor
) : BaseJsonFileStorage<Region>(
    FileProcessor(cacheDir, KEY_CURRENT_REGION, 1),
    namingProcessor,
    Region::class.java
) {

    private companion object {
        const val KEY_CURRENT_REGION = "CURRENT_REGION"
        val DEFAULT_REGION = Region("us", "United States")
    }

    private val regionRelay = BehaviorRelay.createDefault(currentRegion)

    /**
     * @return saved region
     * if the storage is empty saves [DEFAULT_REGION] and returns it
     */
    var currentRegion: Region
        get() {
            return if (contains(KEY_CURRENT_REGION)){
                get(KEY_CURRENT_REGION)!!
            } else {
                put(KEY_CURRENT_REGION, DEFAULT_REGION)
                DEFAULT_REGION
            }
        }
        set(value) {
            put(KEY_CURRENT_REGION, value)
            regionRelay.accept(value)
        }

    /**
     * Observe saved region changes
     */
    fun observeRegionChanges(): Observable<Region> {
        return regionRelay.hide()
    }
}