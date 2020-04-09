package ru.sharipov.podcaster.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val id: Int = 0,
    val parentId: Int = 0,
    val name: String = EMPTY_STRING
) : Parcelable