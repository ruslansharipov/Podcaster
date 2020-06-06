package ru.sharipov.podcaster.f_player_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.layout_player_expanded.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxDialogView
import javax.inject.Inject

class PlayerDialogView : BaseRxDialogView() {

    @Inject
    lateinit var presenter: PlayerDialogPresenter

    @Inject
    lateinit var sh: PlayerStateHolder

    override fun getTheme(): Int = R.style.AppTheme_Light_ModalAnimationDialog

    override fun createConfigurator() = PlayerScreenConfigurator()

    override fun getScreenName(): String = "PlayerDialogView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_player_expanded, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::renderState)
    }

    private fun initView() {
        player_close_ib.setOnClickListener { dismiss() }
        player_play_ib_expanded.setOnClickListener { presenter.onPlayClick() }
        player_pause_ib_expanded.setOnClickListener { presenter.onPauseClick() }
    }

    private fun renderState(state: PlayerState) {
        val episode = state.episode
        player_title_expanded.performIfChanged(episode.title, TextView::setText)
        player_subtitle_expanded.performIfChanged(episode.podcastTitle, TextView::setText)
        player_iv_expanded.performIfChanged(episode.image){ imageUrl: String ->
            bindPicture(imageUrl)
        }
        val isPlaying = state.playbackState is PlaybackState.Playing
        player_play_ib_expanded.performIfChanged(!isPlaying, View::isVisible::set)
        player_pause_ib_expanded.performIfChanged(isPlaying, View::isVisible::set)
    }
}