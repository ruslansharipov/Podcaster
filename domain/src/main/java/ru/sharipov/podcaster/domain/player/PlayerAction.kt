package ru.sharipov.podcaster.domain.player

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.sharipov.podcaster.domain.Episode

sealed class PlayerAction: Parcelable {

    @Parcelize data class Play(val media: List<Episode>, val index: Int = 0) : PlayerAction()
    @Parcelize data class Add(val media: Episode) : PlayerAction()
    @Parcelize data class Seek(val position: Long) : PlayerAction()
    @Parcelize object Next : PlayerAction()
    @Parcelize object Previous : PlayerAction()
    @Parcelize object Resume: PlayerAction()
    @Parcelize object Pause : PlayerAction()
    @Parcelize object Stop : PlayerAction()

}