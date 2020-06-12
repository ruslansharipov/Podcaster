package ru.sharipov.podcaster.base_feature.ui.bus

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.domain.player.QueueData
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.logger.Logger
import javax.inject.Inject

@PerApplication
class PlayerServiceBus @Inject constructor() {

    private val playbackStateRelay = BehaviorRelay.create<PlaybackState>()
    private val queueRelay = BehaviorRelay.create<QueueData>()
    private val actionRelay = PublishRelay.create<PlayerAction>()
    private val positionRelay = BehaviorRelay.create<Int>()

    fun observePlaybackState(): Observable<PlaybackState> {
        return playbackStateRelay.hide()
    }

    fun observeQueue(): Observable<QueueData> {
        return queueRelay.hide()
    }

    fun observePosition(): Observable<Int> {
        return positionRelay.hide()
    }

    fun emitPosition(newPositionSec: Int) {
        Logger.d("positionRelay emit: $positionRelay")
        positionRelay.accept(newPositionSec)
    }

    fun emitAction(action: PlayerAction) {
        actionRelay.accept(action)
    }

    fun emitQueue(queueData: QueueData) {
        queueRelay.accept(queueData)
    }

    fun emitPlaybackState(playbackState: PlaybackState) {
        Logger.d("state: $playbackState")
        playbackStateRelay.accept(playbackState)
    }
}