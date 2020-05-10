package ru.sharipov.podcaster.i_subscription

import ru.sharipov.podcaster.domain.PodcastFull

fun PodcastFull.toSubscription(): Subscription {
    return Subscription(id, publisher, title, image)
}