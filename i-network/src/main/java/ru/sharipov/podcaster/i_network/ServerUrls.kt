package ru.sharipov.podcaster.i_network

/**
 * URL всех серверных запросов
 */
object ServerUrls {
    const val BASE_URL = "https://listen-api.listennotes.com/api/v2/"

    const val BEST_PODCASTS = "/best_podcasts"
    const val CURATED_PODCASTS = "/curated_podcasts"
    const val JUST_LISTEN = "/just_listen"
    const val PODCAST = "/podcasts/{id}"
    const val EPICODES = "/episodes/{id}"
    const val GENRES = "/genres"
    const val REGIONS = "/regions"
    const val LANGUAGES = "/languages"
    const val SEARCH = "/search"
    const val TYPE_AHEAD = "/typeahead"
    //TODO добавить остальные методы
}

object CommonParam {
    const val LIMIT = "limit"
    const val OFFSET = "offset"

    const val ID = "id"
    const val TYPE = "type"
}

