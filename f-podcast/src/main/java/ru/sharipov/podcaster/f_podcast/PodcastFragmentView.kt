package ru.sharipov.podcaster.f_podcast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_podcast.*
import kotlinx.android.synthetic.main.layout_podcast_appbar.*
import kotlinx.android.synthetic.main.layout_podcast_details_shimmer.*
import kotlinx.android.synthetic.main.layout_podcast_toolbar.*
import ru.sharipov.podcaster.base.datalist_date.MergeList
import ru.sharipov.podcaster.base_feature.ui.adapter.PaginationableAdapter
import ru.sharipov.podcaster.base_feature.ui.extesions.*
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderStateView
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.f_podcast.view.SubscribeButton
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import javax.inject.Inject
import kotlin.math.abs

class PodcastFragmentView : BaseRxFragmentView() {

    @Inject
    lateinit var sh: PodcastStateHolder

    @Inject
    lateinit var presenter: PodcastPresenter

    private val easyAdapter = PaginationableAdapter(
        loadingIndicatorRes = R.layout.layout_episode_item_skeleton,
        onShowMoreListener = { presenter.onShowMore() }
    )
    private val episodeController = EpisodeController { presenter.onEpisodeClick(it) }
    private val offsetListener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val threshold = dpToPx(60)
            val isToolbarVisible = threshold < abs(verticalOffset)
            podcast_toolbar_icon_iv.isVisible = isToolbarVisible
            podcast_toolbar_title_tv.isVisible = isToolbarVisible
            podcast_toolbar_publisher_tv.isVisible = isToolbarVisible
        }

    override fun createConfigurator() = PodcastScreenConfigurator(arguments)

    override fun getScreenName(): String = "PodcastFragmentView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_podcast, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::renderState)
    }

    private fun initView() {
        podcast_swr.setOnRefreshListener { presenter.onSwipeRefresh() }
        podcast_toolbar_back_btn.setOnClickListener { presenter.onBackClick() }
        podcast_app_bar.addOnOffsetChangedListener(offsetListener)
        podcast_episodes_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = easyAdapter
        }
        podcast_episodes_pv.errorClickListener = { presenter.onErrorClick() }
        podcast_subscribe_btn.setOnClickListener { presenter.onSubscribeClick() }
    }

    private fun renderState(state: PodcastState) {
        val podcast = state.podcast
        val title = podcast.title
        val publisher = podcast.publisher
        val isSubscribed = state.isSubscribed
        val isDetailsLoading = state.details.isLoading
        podcast_details_shimmer.performIfChanged(isDetailsLoading, View::isVisible::set)
        podcast_swr.performIfChanged(state.episodes.isSwrLoading, SwipeRefreshLayout::setRefreshing)
        podcast_title_tv.performIfChanged(title, TextView::setText)
        podcast_publisher_tv.performIfChanged(publisher, TextView::setText)
        podcast_toolbar_title_tv.performIfChanged(title, TextView::setText)
        podcast_toolbar_publisher_tv.performIfChanged(publisher, TextView::setText)
        podcast_subscribe_btn.performIfChanged(isSubscribed, isDetailsLoading) { _, _ ->
            isVisible = !isDetailsLoading
            setChecked(isSubscribed)
        }
        podcast_icon_iv.performIfChanged(podcast.image) { imageUrl ->
            podcast_icon_iv.bindPicture(imageUrl)
            podcast_toolbar_icon_iv.bindPicture(imageUrl)
        }
        val episodes = state.episodes
        podcast_episodes_pv.performIfChanged(episodes.placeholderState, PlaceholderStateView::setState)
        podcast_episodes_rv.performIfChanged(episodes.data) { mergeBundle ->
            mergeBundle.safeGet { mergeList: MergeList<Episode>, paginationState: PaginationState ->
                easyAdapter.setData(mergeList, episodeController, paginationState)
                podcast_episodes_tv.isVisible = mergeList.isNotEmpty()
            }
        }
        val podcastFull = state.details.data
        podcast_details_tv.performIfChanged(podcastFull?.description) { htmlDescription ->
            text = HtmlCompat.fromHtml(htmlDescription, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
        podcast_episodes_tv.performIfChanged(podcastFull?.totalEpisodes) { episodesCount ->
            text = string(R.string.podcast_episodes_count_format, episodesCount)
        }

    }
}