package ru.sharipov.podcaster.f_episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_episode.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.base_feature.ui.extesions.fromHtmlCompact
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

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::renderState)
    }

    private fun initView() {
        episode_back_btn.setOnClickListener { presenter.onBackPressed() }
    }

    private fun renderState(state: EpisodeState) {
        val episode = state.episode
        episode_icon_iv.bindPicture(episode.image)
        episode_podcast_title_tv.text = state.podcastTitle
        episode_date_tv.text = episode.pubDateMs.toString()
        episode_title_tv.text = episode.title
        episode_details_tv.text = episode.description.fromHtmlCompact()
        episode_state_btn.setState(state.buttonState)
    }
}