package ru.sharipov.podcaster.domain.player

import android.os.Parcelable

interface Media: Parcelable {
    val id: Long get() = 0
    val podcast: String? get() = null
    val title: String? get() = null
    val image: String? get() = null
    val streamUrl: String? get() = null
    val duration: Int? get() = null
}