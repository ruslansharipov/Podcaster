package ru.sharipov.podcaster.base_feature.ui.navigation.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class MainTabType: Parcelable {
    EXPLORE,
    FEED,
    PROFILE
}