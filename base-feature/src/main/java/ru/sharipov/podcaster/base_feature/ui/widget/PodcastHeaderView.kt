package ru.sharipov.podcaster.base_feature.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_podcast_toolbar.view.*
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture

class PodcastHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_podcast_toolbar, this)
        orientation = HORIZONTAL
    }

    fun setTitle(title: String) {
        podcast_toolbar_title_tv.text = title
    }

    fun setSubtitle(subtitle: String) {
        podcast_toolbar_subtitle_tv.text = subtitle
    }

    fun setIcon(url: String) {
        podcast_toolbar_icon_iv.bindPicture(url)
    }
}