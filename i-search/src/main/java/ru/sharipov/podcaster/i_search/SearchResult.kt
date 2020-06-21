package ru.sharipov.podcaster.i_search

import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.PodcastFull

/**
 * The result of user's search
 */
sealed class SearchResult {

    /**
     * @param genre genre picked by user
     */
    data class GenreResult(val genre: Genre): SearchResult()

    /**
     * @param podcast podcast picked by user
     */
    data class PodcastResult(val podcast: PodcastFull): SearchResult()
}