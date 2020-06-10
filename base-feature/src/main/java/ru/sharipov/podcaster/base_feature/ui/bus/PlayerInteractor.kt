package ru.sharipov.podcaster.base_feature.ui.bus

import android.content.Context
import androidx.core.content.ContextCompat
import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.navigation.PlayerServiceRoute
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.domain.player.MediaState
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.i_history.HistoryInteractor
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class PlayerInteractor @Inject constructor(
    private val serviceBus: PlayerServiceBus,
    private val context: Context,
    private val historyInteractor: HistoryInteractor
) {

    fun play(media: Episode) {
        emitAction(PlayerAction.Play(listOf(media)))
        historyInteractor.add(media)
    }

    fun pause() {
        emitAction(PlayerAction.Pause)
    }

    fun seek(position: Long) {
        emitAction(PlayerAction.Seek(position))
    }

    fun stop() {
        emitAction(PlayerAction.Stop)
    }

    fun observeState(id: String): Observable<PlaybackState> {
        return serviceBus
            .observePlaybackState()
            .filter { state ->
                state is MediaState && id == state.media?.id
            }
    }

    fun observeAllStates(): Observable<PlaybackState> {
        return serviceBus.observePlaybackState()
    }

    private fun emitAction(action: PlayerAction) {
        val intent = PlayerServiceRoute(action).prepareIntent(context)
        ContextCompat.startForegroundService(context, intent)
    }
}