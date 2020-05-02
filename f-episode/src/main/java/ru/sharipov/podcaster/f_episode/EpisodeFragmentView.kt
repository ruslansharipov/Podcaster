package ru.sharipov.podcaster.f_episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class EpisodeFragmentView : BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var presenter: EpisodePresenter

    @Inject
    lateinit var sh: EpisodeStateHolder

    override fun createConfigurator() = EpisodeScreenConfigurator(arguments)

    override fun getScreenName(): String = "EpisodeFragmentView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_episode, container, false)
}