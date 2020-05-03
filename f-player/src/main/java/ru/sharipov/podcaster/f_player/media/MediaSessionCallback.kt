package ru.sharipov.podcaster.f_player.media

import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerServiceBus
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.f_player.service.di.PerService
import javax.inject.Inject

@PerService
class MediaSessionCallback @Inject constructor(
    private val playerServiceBus: PlayerServiceBus
) : MediaSessionCompat.Callback() {

    override fun onPlay() {
        playerServiceBus.emitAction(PlayerAction.Resume)
    }

    override fun onSkipToNext() {
        playerServiceBus.emitAction(PlayerAction.Next)
    }

    override fun onSkipToPrevious() {
        playerServiceBus.emitAction(PlayerAction.Previous)
    }

    override fun onPause() {
        playerServiceBus.emitAction(PlayerAction.Pause)
    }

    override fun onSetRepeatMode(repeatMode: Int) {
        // TODO
    }

    override fun onSetShuffleMode(@PlaybackStateCompat.ShuffleMode shuffleMode: Int) {
        // TODO
    }

    override fun onStop() {
        playerServiceBus.emitAction(PlayerAction.Stop)
    }

    override fun onSeekTo(pos: Long) {
        playerServiceBus.emitAction(PlayerAction.Seek(pos))
    }
}