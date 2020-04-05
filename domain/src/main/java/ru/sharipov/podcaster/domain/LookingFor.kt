package ru.sharipov.podcaster.domain

data class LookingFor(
    val cohosts: Boolean = false,
    val crossPromotion: Boolean = false,
    val sponsors: Boolean = false,
    val guests: Boolean = false
)