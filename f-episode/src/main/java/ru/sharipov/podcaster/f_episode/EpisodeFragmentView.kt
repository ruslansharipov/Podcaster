package ru.sharipov.podcaster.f_episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_episode.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.base_feature.ui.extesions.fromHtmlCompact
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.widget.state_button.StateButton
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
        episode_state_btn.setOnClickListener { presenter.onPlayBtnClick() }
    }

    private fun renderState(state: EpisodeState) {
        val episode = state.episode
        episode_state_btn.performIfChanged(state.buttonState, StateButton::setState)
        episode_podcast_title_tv.performIfChanged(state.podcastTitle, TextView::setText)
        episode_date_tv.performIfChanged(episode.pubDateMs.toString(), TextView::setText)
        episode_details_tv.performIfChanged(episode.description.fromHtmlCompact(), TextView::setText)
        episode_icon_iv.performIfChanged(episode.image, { bindPicture(it) } )
        episode_title_tv.performIfChanged(episode.title, TextView::setText)
    }
}