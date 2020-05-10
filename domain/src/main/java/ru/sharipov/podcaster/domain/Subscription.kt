package ru.sharipov.podcaster.domain

interface Subscription {
    val id: String
    val publisher: String
    val title: String
    val image: String
}