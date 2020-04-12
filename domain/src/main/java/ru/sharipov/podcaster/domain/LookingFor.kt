package ru.sharipov.podcaster.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LookingFor(
    val cohosts: Boolean = false,
    val crossPromotion: Boolean = false,
    val sponsors: Boolean = false,
    val guests: Boolean = false
): Parcelable