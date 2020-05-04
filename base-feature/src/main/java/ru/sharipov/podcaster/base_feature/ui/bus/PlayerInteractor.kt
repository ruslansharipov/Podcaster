package ru.sharipov.podcaster.base_feature.ui.bus

import android.content.Context
import android.os.Build
import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.navigation.PlayerServiceRoute
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.domain.player.Media
import ru.sharipov.podcaster.domain.player.MediaState
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class PlayerInteractor @Inject constructor(
    private val serviceBus: PlayerServiceBus,
    private val context: Context
) {

    fun play(media: Media) {
        emitAction(PlayerAction.Play(listOf(media)))
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

    private fun emitAction(action: PlayerAction){
        val intent = PlayerServiceRoute(action).prepareIntent(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }
}