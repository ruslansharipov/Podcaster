package ru.sharipov.podcaster.f_main

import ru.surfstudio.android.core.mvp.activity.BaseRenderableActivityView
import ru.surfstudio.android.core.mvp.presenter.CorePresenter
import ru.surfstudio.android.core.mvp.view.CoreView
import javax.inject.Inject

class MainActivityView: BaseRenderableActivityView<MainScreenModel>() {

    @Inject
    lateinit var presenter: MainPresenter

    override fun createConfigurator() = MainActivityConfigurator(intent)

    override fun getScreenName(): String = "MainActivityView"

    override fun getContentView(): Int = R.layout.activity_main

    override fun getPresenters(): Array<CorePresenter<out CoreView>> = arrayOf(presenter)

    override fun renderInternal(sm: MainScreenModel) {
        // TODO
    }
}