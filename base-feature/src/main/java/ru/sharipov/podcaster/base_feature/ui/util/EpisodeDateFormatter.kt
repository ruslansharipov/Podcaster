package ru.sharipov.podcaster.base_feature.ui.util

import android.content.Context
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.domain.Episode

object EpisodeDateFormatter {

    private const val SHORT_PATTERN = "dd MMM"
    private const val FULL_PATTERN = "dd.MM.yyyy"

    private val dateShortFormatter = DateTimeFormatter.ofPattern(SHORT_PATTERN)
    private val dateFullFormatter = DateTimeFormatter.ofPattern(FULL_PATTERN)

    fun format(context: Context, episode: Episode): String {
        val today = LocalDateTime.now()
        val date = LocalDateTime.ofEpochSecond(episode.pubDateMs / 1000, 0, ZoneOffset.MIN)
        val isToday = date == today
        val isYesterday = today.minusDays(1).toLocalDate() == date.toLocalDate()
        val isThisYear = date.year == today.year
        return when {
            isToday -> context.getString(R.string.episode_date_today)
            isYesterday -> context.getString(R.string.episode_date_yesterday)
            isThisYear -> dateShortFormatter.format(date)
            else -> dateFullFormatter.format(date)
        }
    }
}