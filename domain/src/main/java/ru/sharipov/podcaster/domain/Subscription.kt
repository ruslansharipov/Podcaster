package ru.sharipov.podcaster.domain

import java.io.Serializable

interface Subscription: Serializable {
    val id: String
    val publisher: String
    val title: String
    val image: String
}