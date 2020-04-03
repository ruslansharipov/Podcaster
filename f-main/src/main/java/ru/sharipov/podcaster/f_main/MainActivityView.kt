package ru.sharipov.podcaster.f_main

import android.os.Bundle
import android.os.PersistableBundle
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxActivityView
import javax.inject.Inject

class MainActivityView: BaseRxActivityView() {

    @Inject
    lateinit var presenter: MainPresenter

    override fun createConfigurator() = MainActivityConfigurator(intent)

    override fun getScreenName(): String = "MainActivityView"

    override fun getContentView(): Int = R.layout.activity_main

    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?,
        viewRecreated: Boolean
    ) {
        initView()
        bind()
    }

    private fun initView() {

    }

    private fun bind() {

    }
}