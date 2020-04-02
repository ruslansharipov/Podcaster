package ru.sharipov.podcaster.i_network.cache

import ru.sharipov.podcaster.i_network.ServerUrls
import ru.sharipov.podcaster.i_network.network.HttpMethods
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
        SimpleCacheInfo(HttpMethods.GET, ServerUrls.GENRES, Names.GENRES, 1)
    )

    object Names {
        const val GENRES = "genres"
    }
}
