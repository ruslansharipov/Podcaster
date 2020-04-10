package ru.sharipov.podcaster.f_region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_regions.*
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxBottomSheetDialogFragment
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxPresenter
import ru.surfstudio.android.easyadapter.EasyAdapter
import javax.inject.Inject

class RegionDialogView : BaseRxBottomSheetDialogFragment() {

    @Inject
    lateinit var presenter: RegionPresenter

    @Inject
    lateinit var sh: RegionStateHolder

    private val easyAdapter = EasyAdapter()
    private val regionController = RegionController { presenter.onRegionSelected(it) }

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
        regions_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = easyAdapter
        }
    }

    private fun render(state: RegionState) {
        regions_rv.performIfChanged(state.regions.data){ regions: List<SelectableRegion> ->
            easyAdapter.setData(regions, regionController)
        }
    }
}