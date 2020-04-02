package ru.sharipov.podcaster.i_network.cache

import ru.sharipov.podcaster.i_network.network.cache.SimpleCacheInfo
import javax.inject.Inject

/**
 * Хранилище информации о запросах, требующих кэширования
 */
class SimpleCacheInfoStorage @Inject constructor() {

    /**
     * Заполняется [SimpleCacheInfo].
     */
    val simpleCaches: Collection<SimpleCacheInfo> = listOf(
        // TODO SimpleCacheInfo()
    )

    object Names {

    }
}
