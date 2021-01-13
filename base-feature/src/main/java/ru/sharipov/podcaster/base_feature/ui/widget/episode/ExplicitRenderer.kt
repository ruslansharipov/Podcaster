package ru.sharipov.podcaster.base_feature.ui.widget.episode

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.dpToPx
import ru.sharipov.podcaster.base_feature.ui.extesions.updateDrawables
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.utilktx.ktx.text.SPACE

interface ExplicitRenderer {

    fun TextView.renderExplicitStatus(episode: Episode) {
        updateDrawables(
            right = if (episode.explicitContent) getExplicitDrawable() else null
        )
    }

    fun TextView.renderTitleWithExplicitStatus(episode: Episode) {
        if (episode.explicitContent) {
            val spannableString = SpannableString(episode.title + SPACE)
            val length = spannableString.length
            val explicitDrawable = getExplicitDrawable()
            val drawableMargin = context.dpToPx(4)
            explicitDrawable?.setBounds(
                drawableMargin,
                0,
                explicitDrawable.intrinsicWidth + drawableMargin,
                explicitDrawable.intrinsicHeight
            )

            spannableString.setSpan(
                CenteredImageSpan(explicitDrawable),
                length - 1,
                length,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            setText(spannableString, TextView.BufferType.SPANNABLE)
        } else {
            text = episode.title
        }
    }

    private fun TextView.getExplicitDrawable(): Drawable? {
        return context.getDrawable(R.drawable.ic_explicit_16)?.apply {
            setTint(ContextCompat.getColor(context, R.color.colorAccent))
        }
    }
}