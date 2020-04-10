package ru.sharipov.podcaster.f_region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxBottomSheetDialogFragment
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxPresenter
import javax.inject.Inject

class RegionDialogView : BaseRxBottomSheetDialogFragment() {

    @Inject
    lateinit var presenter: RegionPresenter

    @Inject
    lateinit var sh: RegionStateHolder

    override fun createConfigurator() = RegionScreenConfigurator()

    override fun getPresenters() = emptyArray<BaseRxPresenter>()

    override fun getScreenName(): String = "RegionDialogView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_regions, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::render)
    }

    private fun initView() {

    }

    private fun render(state: RegionState) {

    }
}