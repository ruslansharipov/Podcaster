package ru.sharipov.podcaster.domain

data class TypeAhead(
    val terms: List<String> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val podcasts: List<PodcastTypeAhead> = emptyList()
)