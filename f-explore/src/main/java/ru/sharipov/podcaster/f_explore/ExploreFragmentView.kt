package ru.sharipov.podcaster.f_explore

import ru.surfstudio.android.core.mvp.fragment.BaseRenderableFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class ExploreFragmentView: BaseRenderableFragmentView<ExploreScreenModel>(), CrossFeatureFragment {

    @Inject
    lateinit var presenter: ExplorePresenter

    override fun createConfigurator() = ExploreScreenConfigurator()

    override fun getScreenName(): String = "ExploreFragmentView"

    override fun getPresenters() = arrayOf(presenter)

    override fun renderInternal(sm: ExploreScreenModel?) {

    }
}