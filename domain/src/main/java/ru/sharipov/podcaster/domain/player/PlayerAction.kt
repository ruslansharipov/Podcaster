package ru.sharipov.podcaster.domain.player

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class PlayerAction: Parcelable {

    @Parcelize data class Play(val media: List<Media>, val index: Int) : PlayerAction()
    @Parcelize data class Add(val media: Media) : PlayerAction()
    @Parcelize data class Seek(val position: Long) : PlayerAction()
    @Parcelize object Next : PlayerAction()
    @Parcelize object Previous : PlayerAction()
    @Parcelize object Resume: PlayerAction()
    @Parcelize object Pause : PlayerAction()
    @Parcelize object Stop : PlayerAction()

}