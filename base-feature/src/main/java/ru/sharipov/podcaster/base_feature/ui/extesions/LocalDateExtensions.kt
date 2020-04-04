package ru.sharipov.podcaster.base_feature.ui.extesions

import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.util.Locale

/**
 * Extension-метод для форматирования даты в строку.
 *
 * @return строка, вида: "17 апреля".
 * Если в дате год не совпадает с текущим (в данный момент),
 * то строка будет включать в себя также и год: "17 апреля 2019".
 * */
fun LocalDate.formatHumanReadable(): String {
    val now = LocalDate.now()
    val shouldDisplayYear = now.year - year != 0
    val monthText = month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    return when {
        shouldDisplayYear -> "$dayOfMonth $monthText $year"
        else -> "$dayOfMonth $monthText"
    }
}
