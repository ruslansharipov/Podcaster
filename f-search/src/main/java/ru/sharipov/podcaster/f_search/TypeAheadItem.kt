package ru.sharipov.podcaster.f_search

import androidx.annotation.StringRes
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.PodcastTypeAhead

sealed class TypeAheadItem {

    data class TitleItem(@StringRes val titleRes: Int) : TypeAheadItem()
    data class TermItem(val term: String) : TypeAheadItem()
    data class GenreItem(val genre: Genre) : TypeAheadItem()
    data class PodcastItem(val podcast: PodcastTypeAhead) : TypeAheadItem()
}