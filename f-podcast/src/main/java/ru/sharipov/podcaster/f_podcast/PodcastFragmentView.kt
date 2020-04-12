package ru.sharipov.podcaster.f_podcast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import javax.inject.Inject

class PodcastFragmentView : BaseRxFragmentView() {

    @Inject
    lateinit var sh: PodcastStateHolder

    @Inject
    lateinit var presenter: PodcastPresenter

    override fun createConfigurator() = PodcastScreenConfigurator(arguments)

    override fun getScreenName(): String = "PodcastFragmentView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_podcast, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {

    }
}