package ru.sharipov.podcaster.base_feature.ui.widget.episode

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_episode.view.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.string
import ru.sharipov.podcaster.domain.Episode

class EpisodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private val dateShortFormatter = DateTimeFormatter.ofPattern("dd MMM")
        private val dateFullFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    }

    private val today = LocalDateTime.now()

    init {
        View.inflate(context, R.layout.view_episode, this)
    }

    fun setEpisode(episode: Episode) {
        episode_title_tv.text = episode.title
        episode_date_tv.text = createFormattedDate(episode)
        episode_length_tv.text = createFormattedLength(episode)
    }

    private fun createFormattedDate(episode: Episode): String {
        val date = LocalDateTime.ofEpochSecond(episode.pubDateMs / 1000, 0, ZoneOffset.MIN)
        val isToday = date == today
        val isYesterday = today.minusDays(1).dayOfMonth == date.dayOfMonth
        val isThisYear = date.year == today.year
        return when {
            isToday -> string(R.string.episode_date_today)
            isYesterday -> string(R.string.episode_date_yesterday)
            isThisYear -> dateShortFormatter.format(date)
            else -> dateFullFormatter.format(date)
        }
    }

    private fun createFormattedLength(episode: Episode): String {
        val time = LocalTime.ofSecondOfDay(episode.audioLengthSec.toLong())
        return if (time.hour != 0) {
            string(R.string.episode_length_format_full, time.hour, time.minute)
        } else {
            string(R.string.episode_length_format_short, time.minute)
        }
    }
}