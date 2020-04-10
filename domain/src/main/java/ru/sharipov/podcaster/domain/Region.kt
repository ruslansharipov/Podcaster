package ru.sharipov.podcaster.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Region(
    val code: String = EMPTY_STRING,
    val name: String = EMPTY_STRING
) : Parcelable {

    val flagUrl: String
        get() = "https://www.countryflags.io/$code/flat/64.png"
}