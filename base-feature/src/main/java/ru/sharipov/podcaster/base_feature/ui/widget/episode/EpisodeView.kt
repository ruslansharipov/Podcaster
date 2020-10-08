package ru.sharipov.podcaster.base_feature.ui.widget.episode

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_episode.view.*
import org.threeten.bp.LocalTime
import ru.sharipov.podcaster.base_feature.ui.util.EpisodeDateFormatter
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPictureDefault
import ru.sharipov.podcaster.base_feature.ui.extesions.distinctText
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.extesions.string
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.core.mvp.binding.rx.ui.CoreRxConstraintLayoutView
import ru.surfstudio.android.core.mvp.presenter.CorePresenter
import ru.surfstudio.android.core.mvp.view.CoreView

class EpisodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ExplicitRenderer {

    private var episode: Episode? = null

    init {
        View.inflate(context, R.layout.view_episode, this)
        setBackgroundResource(R.drawable.bg_selectable_item_divider)
    }

    fun setEpisode(episode: Episode) {
        episode_length_tv.distinctText = createFormattedLength(episode.duration)
        episode_title_tv.distinctText = episode.title
        episode_date_tv.distinctText = EpisodeDateFormatter.format(context, episode)
        episode_date_tv.performIfChanged(episode){
            renderExplicitStatus(episode)
        }
        episode_icon_iv.performIfChanged(episode.image){
            bindPictureDefault(episode.image)
        }
    }

    private fun createFormattedLength(seconds: Int): String {
        val time = LocalTime.ofSecondOfDay(seconds.toLong())
        return if (time.hour != 0) {
            string(R.string.episode_length_format_full, time.hour, time.minute)
        } else {
            string(R.string.episode_length_format_short, time.minute)
        }
    }
}