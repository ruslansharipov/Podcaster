package ru.sharipov.podcaster.domain.player

import android.os.Parcelable
import ru.sharipov.podcaster.domain.EMPTY_STRING

interface Media: Parcelable {
    val id: String get() = EMPTY_STRING
    val podcast: String? get() = null
    val title: String? get() = null
    val image: String? get() = null
    val streamUrl: String? get() = null
    val duration: Int? get() = null
}