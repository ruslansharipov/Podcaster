package ru.sharipov.podcaster.f_main

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import kotlinx.android.synthetic.main.activity_main.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPictureDefault
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.f_main.di.MainActivityConfigurator
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxActivityView
import ru.surfstudio.android.core.ui.FragmentContainer
import javax.inject.Inject

class MainActivityView : BaseRxActivityView(), FragmentContainer {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var sh: MainStateHolder

    override fun createConfigurator() = MainActivityConfigurator(intent)

    override fun getScreenName(): String = "MainActivityView"

    override fun getContentView(): Int = R.layout.activity_main

    override fun getContentContainerViewId(): Int = R.id.main_fragment_container

    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?,
        viewRecreated: Boolean
    ) {
        initView()
        sh.bindTo(::render)
    }

    private fun initView() {
        main_tab_view.selectedTabObservable.bindTo(presenter::onBottomTabClick)
        main_player_collapsed.setOnClickListener { presenter.onPlayerClick() }
        main_play_ib_collapsed.setOnClickListener { presenter.onPlayPauseClick() }
        main_container.addOnInsetsChangedListener { presenter.onInsetsChange(it) }
    }

    private fun render(state: MainState) {
        val lastPlayedEpisode = state.lastPlayed.getOrNull()
        val insets = state.insets
        val isCollapsedPlayerVisible = lastPlayedEpisode != null && !insets.hasKeyboard

        main_player_collapsed.performIfChanged(isCollapsedPlayerVisible, View::isVisible::set)
        main_title_collapsed.performIfChanged(lastPlayedEpisode?.title, TextView::setText)
        main_subtitle_collapsed.performIfChanged(lastPlayedEpisode?.podcastTitle, TextView::setText)
        main_iv_collapsed.performIfChanged(lastPlayedEpisode?.image, ImageView::bindPictureDefault)

        main_container.performIfChanged(insets.keyboard, { updatePadding(bottom = it) })
        main_tab_view.performIfChanged(state.currentTabType, insets.hasKeyboard, { currentTab, hasKeyboard ->
            selectedTabType = currentTab
            isVisible = !hasKeyboard
        })
        main_pb_collapsed.performIfChanged(
            lastPlayedEpisode?.duration,
            state.position,
            state.bufferingPosition,
            { duration, position, bufferedPosition ->
                max = duration
                progress = position
                secondaryProgress = bufferedPosition
            }
        )
        main_play_ib_collapsed.setState(state.playbackState)
    }
}