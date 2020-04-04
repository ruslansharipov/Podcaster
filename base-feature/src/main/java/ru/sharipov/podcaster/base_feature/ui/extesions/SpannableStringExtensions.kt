package ru.sharipov.podcaster.base_feature.ui.extesions

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View

/**
 * Задать цвет [SpannableString]
 *
 * @param colorHex hex цвета
 * @param startIndex индекс символа, с которого начать закрашивание
 * @param endIndex индекс символа, на котором закончить окрашивание
 */
fun SpannableString.applyColor(colorHex: Int, startIndex: Int = 0, endIndex: Int = length): SpannableString {
    val colorSpan = ForegroundColorSpan(colorHex)
    setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * Сделать текст жирным
 * @param startIndex индекс символа, с которого начать ожирение
 * @param endIndex индекс символа, на котором закончить ожирение
 */
fun SpannableString.makeBold(startIndex: Int = 0, endIndex: Int = length): SpannableString {
    val boldSpan = StyleSpan(Typeface.BOLD)
    setSpan(boldSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * Задать действие по событию клика
 *
 * @param onClickAction действие, происходящее при клике
 */
fun SpannableString.applyOnClickAction(
    onClickAction: () -> Unit,
    startIndex: Int = 0,
    endIndex: Int = length
): SpannableString {
    val clickableSpan = object : ClickableSpan() {

        override fun onClick(widget: View?) {
            onClickAction()
        }

        override fun updateDrawState(ds: TextPaint?) {
            ds?.isUnderlineText = false
        }
    }
    setSpan(clickableSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * Задать действие по событию клика
 *
 * @param onClickAction действие, происходящее при клике
 */
fun SpannableString.applyOnClickAction(
    onClickAction: () -> Unit
): SpannableString {
    return applyOnClickAction(onClickAction, 0, length)
}

/**
 * Обрезать и добавить многоточие в конце строки, если она больше заданного размера
 *
 * @param maxLength максимальный размер строки
 */
fun SpannableString.ellipsizeEnd(maxLength: Int): SpannableString {
    return if (length > maxLength) {
        val spannableStringBuilder =
            SpannableStringBuilder()
                .append(subSequence(0, maxLength))
                .append('…')
        SpannableString(spannableStringBuilder)
    } else {
        this
    }
}
