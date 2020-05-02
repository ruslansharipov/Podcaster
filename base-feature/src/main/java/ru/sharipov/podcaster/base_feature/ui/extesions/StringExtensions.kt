package ru.sharipov.podcaster.base_feature.ui.extesions

import android.text.Spanned
import androidx.core.text.HtmlCompat

fun String.fromHtmlCompact() : Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT)
}