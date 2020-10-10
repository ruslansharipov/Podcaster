package ru.sharipov.podcaster.i_history.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.sharipov.podcaster.domain.PodcastShort
import ru.sharipov.podcaster.domain.Subscription
import ru.sharipov.podcaster.i_network.network.Transformable

@Entity(tableName = "subscription_entity")
class SubscriptionEntity(
    @PrimaryKey val id: String,
    val publisher: String,
    val title: String,
    val image: String
): Transformable<PodcastShort> {

    override fun transform(): PodcastShort {
        return PodcastShort(id, publisher, title, image)
    }
}

fun Subscription.toSubscriptionEntity() : SubscriptionEntity {
    return SubscriptionEntity(id, publisher, title, image)
}