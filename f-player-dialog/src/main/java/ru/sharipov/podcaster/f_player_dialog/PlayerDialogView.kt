package ru.sharipov.podcaster.f_player_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_player_expanded.*
import ru.sharipov.podcaster.base_feature.ui.extesions.*
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxBottomSheetDialogFragment
import ru.surfstudio.android.core.mvp.presenter.CorePresenter
import ru.surfstudio.android.core.mvp.view.CoreView
import ru.surfstudio.android.logger.Logger
import javax.inject.Inject

class PlayerDialogView : BaseRxBottomSheetDialogFragment() {

    @Inject
    lateinit var presenter: PlayerDialogPresenter

    @Inject
    lateinit var sh: PlayerStateHolder

    override fun createConfigurator() = PlayerScreenConfigurator()

    override fun getPresenters() = emptyArray<CorePresenter<CoreView>>()

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
        player_subtitle_expanded.setOnClickListener { presenter.onSubtitleClick() }
        player_play_btn.setOnClickListener { presenter.onStateBtnClick() }
        player_replay_btn.setOnClickListener { presenter.onReplayClick() }
        player_forward_btn.setOnClickListener { presenter.onForwardClick() }
        player_sb_expanded.setOnUserSeekChanges{ presenter.onUserSeeks(it) }
    }

    private fun renderState(state: PlayerState) {
        Logger.d("episode: ${state.episode}")
        val episode = state.episode

        player_position_tv.performIfChanged(state.positionUi, TextView::setText)
        player_remain_tv.performIfChanged(state.remainsUi, TextView::setText)
        player_subtitle_expanded.performIfChanged(episode?.title, TextView::setText)
        player_title_expanded.performIfChanged(episode?.podcastTitle, TextView::setText)
        player_iv_expanded.performIfChanged(episode?.image, ImageView::bindPictureDefault)
        player_sb_expanded.performIfChanged(
            episode?.duration,
            state.position,
            state.bufferingPosition,
            { duration, position, bufferedPosition ->
                max = duration
                progress = position
                secondaryProgress = bufferedPosition
            }
        )
        player_play_btn.setState(state.playbackState)
    }
}