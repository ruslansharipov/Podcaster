package ru.sharipov.podcaster.i_history.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import ru.sharipov.podcaster.i_history.entity.SubscriptionEntity

@Dao
interface SubscriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubscription(entity: SubscriptionEntity): Completable

    @Query("SELECT * FROM subscription_entity")
    fun observeSubscriptionEntities(): Flowable<List<SubscriptionEntity>>

    @Delete
    fun deleteSubscription(entity: SubscriptionEntity): Completable

    @Query("SELECT EXISTS(SELECT * FROM subscription_entity WHERE id=:podcastId LIMIT 1)")
    fun observeIsSubscribed(podcastId: String) : Flowable<Boolean>
}