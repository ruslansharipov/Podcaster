package ru.sharipov.podcaster.f_main.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.layout_player_collapsed.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.f_main.R
import ru.surfstudio.android.core.mvp.binding.rx.extensions.Optional
import ru.surfstudio.android.logger.Logger

class PlayerCollapsedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_player_collapsed, this)
        setBackgroundResource(R.drawable.bg_selectable_item)
    }

    fun render(
        state: PlaybackState,
        optionalMedia: Optional<Episode>,
        positionSec: Int = 0,
        bufferedSec: Int = 0
    ) {
        // TODO найти где не переключается поток
        if (optionalMedia.hasValue) {
            val media = optionalMedia.get()
            player_title_collapsed.text = media.title
            player_subtitle_collapsed.text = media.podcastTitle
            player_iv_collapsed.bindPicture(media.image)
            player_pb_collapsed.max = media.duration
        }
        player_play_ib_collapsed.setState(state)
        isVisible = optionalMedia.hasValue
        player_pb_collapsed.progress = positionSec
        player_pb_collapsed.secondaryProgress = bufferedSec
        Logger.d(
            "PlayerCollapseView.render\n" +
                    "state: $state\n " +
                    "optionalMedia: $optionalMedia\n " +
                    "position: $positionSec"
        )
    }

    fun onPlayPauseClick(listener: () -> Unit){
        player_play_ib_collapsed.setOnClickListener { listener() }
    }
}