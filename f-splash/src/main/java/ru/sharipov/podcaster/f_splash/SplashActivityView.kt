package ru.sharipov.podcaster.f_splash

import ru.surfstudio.android.core.mvp.activity.CoreActivityView
import ru.surfstudio.android.core.mvp.presenter.CorePresenter
import ru.surfstudio.android.core.mvp.view.CoreView
import javax.inject.Inject

class SplashActivityView: CoreActivityView() {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun createConfigurator() = SplashActivityConfigurator(intent)

    override fun getScreenName(): String = "SplashActivityView"

    override fun getContentView(): Int = R.layout.activity_splash

    override fun getPresenters(): Array<CorePresenter<out CoreView>> = arrayOf(presenter)
}