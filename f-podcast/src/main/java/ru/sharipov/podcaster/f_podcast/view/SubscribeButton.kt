package ru.sharipov.podcaster.f_podcast.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_subscribe_btn.view.*
import ru.sharipov.podcaster.f_podcast.R

class SubscribeButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_subscribe_btn, this)

        orientation = HORIZONTAL
        isClickable = true
        isFocusable = true
    }

    fun setChecked(isChecked: Boolean) {
        val (iconRes, titleRes, backgroundRes) = if (isChecked) {
            Triple(
                R.drawable.ic_check_circle,
                R.string.podcast_subscribe_btn_checked_text,
                R.drawable.bg_primary_gray_btn
            )
        } else {
            Triple(
                R.drawable.ic_add_circle,
                R.string.podcast_subscribe_btn_unchecked_text,
                R.drawable.bg_primary_btn
            )
        }
        setBackgroundResource(backgroundRes)
        subscribe_btn_icon.setImageResource(iconRes)
        subscribe_btn_tv.setText(titleRes)
    }
}