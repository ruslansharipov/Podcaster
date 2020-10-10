package ru.sharipov.podcaster.i_history.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import ru.sharipov.podcaster.i_history.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlayedHistory(entity: HistoryEntity): Completable

    @Query("SELECT * FROM history_entity ORDER BY lastPlayedTime DESC")
    fun getHistoryEntities(): Flowable<List<HistoryEntity>>

    @Query("SELECT * FROM history_entity ORDER BY lastPlayedTime DESC LIMIT 1")
    fun getLastPlayed(): Flowable<HistoryEntity>

    @Query("SELECT progress FROM history_entity WHERE id=:episodeId")
    fun getSavedProgress(episodeId: String): Maybe<Int>

//    @Query("SELECT * FROM HistoryEntity WHERE id=:id ")
//    fun getHistoryFor(id: String): Flowable<HistoryEntity>
}