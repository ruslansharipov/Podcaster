package ru.sharipov.podcaster.f_main

import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import kotlinx.android.synthetic.main.activity_main.*
import ru.sharipov.podcaster.base_feature.ui.data.AppInsets
import ru.sharipov.podcaster.base_feature.ui.extesions.dpToPx
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.f_main.di.MainActivityConfigurator
import ru.sharipov.podcaster.f_main.view.BottomTabView
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxActivityView
import ru.surfstudio.android.core.ui.FragmentContainer
import ru.surfstudio.android.logger.Logger
import javax.inject.Inject

class MainActivityView: BaseRxActivityView(), FragmentContainer {

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
        main_player_collapsed.onPlayPauseClick { presenter.onPlayPauseClick() }
        main_container.addOnInsetsChangedListener{ insets: AppInsets ->
            Logger.d("$insets")

            main_tab_view.isVisible = !insets.hasKeyboard
            main_player_collapsed.isVisible = !insets.hasKeyboard
            main_container.updatePadding(bottom = insets.keyboard)
        }
    }

    private fun render(state: MainState) {
        main_tab_view.performIfChanged(state.currentTabType, BottomTabView::selectedTabType::set)
        main_fragment_container.performIfChanged(state.lastPlayed) { optionalMedia ->
            updatePadding(
                bottom = if (optionalMedia.hasValue) dpToPx(48) else 0
            )
        }
        main_player_collapsed.render(state.playbackState, state.lastPlayed)
    }
}