package ru.sharipov.podcaster.f_main

import android.os.Bundle
import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.sharipov.podcaster.f_main.di.MainActivityConfigurator
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxActivityView
import javax.inject.Inject

class MainActivityView: BaseRxActivityView() {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var sh: MainStateHolder

    override fun createConfigurator() = MainActivityConfigurator(intent)

    override fun getScreenName(): String = "MainActivityView"

    override fun getContentView(): Int = R.layout.activity_main

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
    }

    private fun render(state: MainState) {
        main_tab_view.selectedTabType = state.currentTabType
    }
}