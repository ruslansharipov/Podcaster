package ru.sharipov.podcaster.f_episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_episode.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPictureDefault
import ru.sharipov.podcaster.base_feature.ui.extesions.fromHtmlCompact
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.util.EpisodeDateFormatter
import ru.sharipov.podcaster.base_feature.ui.util.TimeFormatter
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxBottomSheetDialogFragment
import ru.surfstudio.android.core.mvp.presenter.CorePresenter
import ru.surfstudio.android.core.mvp.view.CoreView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class EpisodeFragmentView : BaseRxBottomSheetDialogFragment(), CrossFeatureFragment {

    @Inject
    lateinit var presenter: EpisodePresenter

    @Inject
    lateinit var sh: EpisodeStateHolder

    override fun createConfigurator() = EpisodeScreenConfigurator(arguments)

    override fun getPresenters() = emptyArray<CorePresenter<CoreView>>()

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
        episode_state_btn.setOnClickListener { presenter.onPlayBtnClick() }
        episode_share_btn.setOnClickListener { presenter.onShareClick() }
        episode_favourite_btn.setOnClickListener { presenter.onFavoriteClick() }
    }

    private fun renderState(state: EpisodeState) {
        val episode = state.episode
        val image = episode.image
        val dateFormatted = EpisodeDateFormatter.format(requireContext(), state.episode)
        val lengthFormatted = TimeFormatter.fromSeconds(episode.duration)

        episode_iv.performIfChanged(image, ImageView::bindPictureDefault)
        episode_date_tv.performIfChanged(dateFormatted, TextView::setText)
        episode_title_tv.performIfChanged(episode.title, TextView::setText)
        episode_length_tv.performIfChanged(lengthFormatted, TextView::setText)
        episode_details_tv.performIfChanged(episode.description.fromHtmlCompact(), TextView::setText)
        episode_podcast_title_tv.performIfChanged(episode.podcastTitle, TextView::setText)

        episode_state_btn.setState(state.playbackState)
    }
}