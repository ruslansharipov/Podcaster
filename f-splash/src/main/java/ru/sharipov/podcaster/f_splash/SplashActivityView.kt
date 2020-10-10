package ru.sharipov.podcaster.f_splash

import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxActivityView
import javax.inject.Inject

class SplashActivityView: BaseRxActivityView() {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun createConfigurator() = SplashActivityConfigurator(intent)

    override fun getScreenName(): String = "SplashActivityView"

    override fun getContentView(): Int = R.layout.activity_splash
}