package ru.sharipov.podcaster.domain

data class Genre(
    val id: Int = 0,
    val parentId: Int = 0,
    val name: String = EMPTY_STRING
)