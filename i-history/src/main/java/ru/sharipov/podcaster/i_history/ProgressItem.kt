package ru.sharipov.podcaster.i_history

/**
 * Internal model for observing progress changes
 *
 * @param episodeId     id of the episode
 * @param progressSec   saved playback progress
 */
internal data class ProgressItem(
    val episodeId: String,
    val progressSec: Int
)