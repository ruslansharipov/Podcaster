package ru.sharipov.podcaster.f_explore.explore

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
}