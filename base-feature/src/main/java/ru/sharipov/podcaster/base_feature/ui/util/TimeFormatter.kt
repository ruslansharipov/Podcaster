package ru.sharipov.podcaster.base_feature.ui.util

import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

object TimeFormatter {

    private const val SHORT_TIME_PATTERN = "mm:ss"

    private val timeShortFormatter = DateTimeFormatter.ofPattern(SHORT_TIME_PATTERN)
    private val timeFullFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    fun fromSeconds(length: Int?): String {
        val time = LocalTime.ofSecondOfDay(length?.toLong() ?: 0L)
        val formatter = if (time.hour != 0) timeFullFormatter else timeShortFormatter
        return formatter.format(time)
    }
}