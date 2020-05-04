package ru.sharipov.podcaster.base_feature.ui.widget.episode

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_episode.view.*
import org.threeten.bp.LocalTime
import ru.sharipov.podcaster.base_feature.ui.util.EpisodeDateFormatter
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.string
import ru.sharipov.podcaster.domain.Episode

class EpisodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val dateFormatter = EpisodeDateFormatter(context)

    init {
        View.inflate(context, R.layout.view_episode, this)
        setBackgroundResource(R.drawable.bg_selectable_item_divider)
    }

    fun setEpisode(episode: Episode) {
        episode_title_tv.text = episode.title
        episode_date_tv.text = dateFormatter.format(episode)
        episode_length_tv.text = createFormattedLength(episode)
    }

    private fun createFormattedLength(episode: Episode): String {
        val time = LocalTime.ofSecondOfDay(episode.duration.toLong())
        return if (time.hour != 0) {
            string(R.string.episode_length_format_full, time.hour, time.minute)
        } else {
            string(R.string.episode_length_format_short, time.minute)
        }
    }
}