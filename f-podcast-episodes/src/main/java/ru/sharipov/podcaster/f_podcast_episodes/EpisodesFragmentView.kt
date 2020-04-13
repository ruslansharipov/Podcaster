package ru.sharipov.podcaster.f_podcast_episodes

import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class EpisodesFragmentView: BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var sh: EpisodesStateHolder

    @Inject
    lateinit var presenter: EpisodesPresenter

    override fun getScreenName(): String = "EpisodesFragmentView"

    override fun createConfigurator() = EpisodesScreenConfigurator(arguments)
}