package ru.sharipov.podcaster.domain

data class CuratedItem(
    val description: String = EMPTY_STRING,
    val id: String = EMPTY_STRING,
    val listenNotesUrl: String = EMPTY_STRING,
    val podcasts: List<PodcastShort> = emptyList(),
    val pubDateMs: Long = 0,
    val sourceDomain: String = EMPTY_STRING,
    val sourceUrl: String = EMPTY_STRING,
    val title: String = EMPTY_STRING
)