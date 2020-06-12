package ru.sharipov.podcaster.base_feature.ui.bus

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.domain.player.QueueData
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class PlayerServiceBus @Inject constructor() {

    private val playbackStateRelay = BehaviorRelay.create<PlaybackState>()
    private val queueRelay = BehaviorRelay.create<QueueData>()
    private val actionRelay = PublishRelay.create<PlayerAction>()
    private val positionRelay = BehaviorRelay.create<Int>()
    private val bufferedRelay = BehaviorRelay.create<Int>()

    fun observePlaybackState(): Observable<PlaybackState> {
        return playbackStateRelay.hide()
    }

    fun observeQueue(): Observable<QueueData> {
        return queueRelay.hide()
    }

    fun observePosition(): Observable<Int> {
        return positionRelay
            .hide()
            .distinctUntilChanged()
    }

    fun emitPosition(newPositionSec: Int) {
        positionRelay.accept(newPositionSec)
    }

    fun emitAction(action: PlayerAction) {
        actionRelay.accept(action)
    }

    fun emitQueue(queueData: QueueData) {
        queueRelay.accept(queueData)
    }

    fun emitPlaybackState(playbackState: PlaybackState) {
        playbackStateRelay.accept(playbackState)
    }

    fun emitBufferedPosition(newBufferedPositionSec: Int){
        bufferedRelay.accept(newBufferedPositionSec)
    }

    fun observeBufferedPosition(): Observable<Int>{
        return bufferedRelay
            .hide()
            .distinctUntilChanged()
    }
}