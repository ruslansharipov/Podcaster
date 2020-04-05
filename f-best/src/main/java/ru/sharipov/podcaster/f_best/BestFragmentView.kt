package ru.sharipov.podcaster.f_best

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.sharipov.podcaster.f_best.di.BestScreenConfigurator
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class BestFragmentView : BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var presenter: BestFragmentPresenter

    @Inject
    lateinit var sh: BestStateHolder

    override fun createConfigurator() =
        BestScreenConfigurator()

    override fun getScreenName(): String = "BestFragmentView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_best, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        sh.bindTo(::render)
    }

    private fun render(state: BestState) {

    }
}