package ru.sharipov.podcaster.f_region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_regions.*
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.extesions.textChangesStringSkipFirst
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderStateView
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxDialogView
import ru.surfstudio.android.easyadapter.EasyAdapter
import javax.inject.Inject

class RegionDialogView : BaseRxDialogView() {

    @Inject
    lateinit var presenter: RegionPresenter

    @Inject
    lateinit var sh: RegionStateHolder

    private val easyAdapter = EasyAdapter()
    private val regionController = RegionController { presenter.onRegionSelected(it) }

    override fun createConfigurator() = RegionScreenConfigurator()

    override fun getScreenName(): String = "RegionDialogView"

    override fun getTheme(): Int = R.style.AppTheme_Light_ModalAnimationDialog

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
        regions_sv.errorClickListener = { presenter.onErrorClick() }
        regions_back_iv.setOnClickListener { presenter.onBackClick() }
        regions_clear_iv.setOnClickListener { presenter.onClearClick() }
        regions_et.textChangesStringSkipFirst().bindTo(presenter::onInputChange)
    }

    private fun render(state: RegionState) {
        regions_et.performIfChanged(state.isInputEnabled, View::setEnabled)
        regions_clear_iv.performIfChanged(state.isClearBtnVisible, View::isVisible::set)
        regions_sv.performIfChanged(state.placeholderState, PlaceholderStateView::setState)
        regions_rv.performIfChanged(state.regionsList) { regions: List<SelectableRegion> ->
            easyAdapter.setData(regions, regionController)
        }
    }
}