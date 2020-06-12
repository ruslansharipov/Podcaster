package ru.sharipov.podcaster.f_player_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_player_expanded.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.base_feature.ui.extesions.distinctText
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxBottomSheetDialogFragment
import ru.surfstudio.android.core.mvp.presenter.CorePresenter
import ru.surfstudio.android.core.mvp.view.CoreView
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
        player_play_btn.setOnClickListener { presenter.onStateBtnClick() }
    }

    private fun renderState(state: PlayerState) {
        val episode = state.episode
        player_title_expanded.distinctText = episode.title
        player_subtitle_expanded.distinctText = episode.podcastTitle
        player_iv_expanded.performIfChanged(episode.image){ imageUrl: String ->
            bindPicture(imageUrl)
        }
        player_play_btn.setState(state.playbackState)
    }
}