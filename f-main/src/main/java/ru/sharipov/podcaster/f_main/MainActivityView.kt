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
import ru.sharipov.podcaster.f_main.view.BottomTabView
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxActivityView
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.navigation.provider.container.TabFragmentNavigationContainer
import javax.inject.Inject

class MainActivityView : BaseRxActivityView(), TabFragmentNavigationContainer {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var sh: MainStateHolder

    override fun createConfigurator() = MainActivityConfigurator(intent)

    override fun getScreenName(): String = "MainActivityView"

    override fun getContentView(): Int = R.layout.activity_main

    override val containerId: Int = R.id.main_fragment_container

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
        main_container.addOnInsetsChangedListener { Logger.d("insets: $it") }
    }

    private fun render(state: MainState) {
        val lastPlayedEpisode = state.lastPlayed.getOrNull()
        val isCollapsedPlayerVisible = lastPlayedEpisode != null

        main_player_collapsed.performIfChanged(isCollapsedPlayerVisible, View::isVisible::set)
        main_title_collapsed.performIfChanged(lastPlayedEpisode?.title, TextView::setText)
        main_subtitle_collapsed.performIfChanged(lastPlayedEpisode?.podcastTitle, TextView::setText)
        main_iv_collapsed.performIfChanged(lastPlayedEpisode?.image, ImageView::bindPictureDefault)
        main_tab_view.performIfChanged(state.currentTabType, BottomTabView::selectedTabType::set)
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