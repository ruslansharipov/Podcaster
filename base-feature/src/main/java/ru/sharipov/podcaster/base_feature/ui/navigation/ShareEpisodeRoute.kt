package ru.sharipov.podcaster.base_feature.ui.navigation

import android.content.Context
import android.content.Intent
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.core.ui.navigation.activity.route.ActivityRoute

/**
 * Opens chooser of the application to share the episode link with
 *
 * @param episode episode to share
 */
class ShareEpisodeRoute(private val episode: Episode): ActivityRoute() {

    override fun prepareIntent(context: Context?): Intent {
        val shareIntent = Intent()
            .setAction(Intent.ACTION_SEND)
            .putExtra(Intent.EXTRA_TEXT, episode.sharedText)
            .setType("text/plain")
        return Intent.createChooser(shareIntent, episode.title)
    }

    private val Episode.sharedText : String
        get() = """
            $podcastTitle
            $title
            $link
        """.trimIndent()
}