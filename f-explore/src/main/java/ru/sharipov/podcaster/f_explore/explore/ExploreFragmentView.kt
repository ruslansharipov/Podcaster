package ru.sharipov.podcaster.f_explore.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_explore.*
import ru.sharipov.podcaster.f_explore.R
import ru.sharipov.podcaster.f_explore.explore.di.ExploreScreenConfigurator
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class ExploreFragmentView: BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var presenter: ExplorePresenter

    @Inject
    lateinit var sh: ExploreStateHolder

    override fun createConfigurator() = ExploreScreenConfigurator()

    override fun getScreenName(): String = "ExploreFragmentView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_explore, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::render)
    }

    private fun initView() {
        explore_vp.adapter = ExplorePagerAdapter(childFragmentManager, resources)
        explore_tl.setupWithViewPager(explore_vp)
    }

    private fun render(state: ExploreState) {

    }
}