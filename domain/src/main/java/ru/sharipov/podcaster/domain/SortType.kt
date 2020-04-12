package ru.sharipov.podcaster.domain

enum class SortType(val id: String) {
    RECENT_FIRST("recent_first"),
    OLDEST_FIRST("oldest_first");

    companion object {

        fun getById(id: String): SortType {
            return values()
                .firstOrNull { it.id == id }
                ?: RECENT_FIRST
        }
    }
}