package ru.sharipov.podcaster.f_episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_episode.*
import ru.sharipov.podcaster.base_feature.ui.extesions.fromHtmlCompact
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
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
        episode_toolbar_back_btn.setOnClickListener { presenter.onBackPressed() }
        episode_state_btn.setOnClickListener { presenter.onPlayBtnClick() }
    }

    private fun renderState(state: EpisodeState) {
        val episode = state.episode
        val image = episode.image
        val title = state.podcastTitle
        val dateFormatted = state.dateFormatted
//        episode_state_btn.performIfChanged(state.playbackState, StateButton::setState)
        episode_state_btn.setState(state.playbackState)
        episode_podcast_toolbar.performIfChanged(title, dateFormatted, image) { _, _, _ ->
            setTitle(title)
            setSubtitle(dateFormatted)
            setIcon(image)
        }
        episode_details_tv.performIfChanged(episode.description.fromHtmlCompact(), TextView::setText)
        episode_title_tv.performIfChanged(episode.title, TextView::setText)
    }
}