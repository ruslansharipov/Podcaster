package ru.sharipov.podcaster.f_best

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_best.*
import ru.sharipov.podcaster.base_feature.ui.adapter.PaginationableAdapter
import ru.sharipov.podcaster.base_feature.ui.extesions.isSwrLoading
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.extesions.placeholderState
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderStateView
import ru.sharipov.podcaster.base_feature.ui.widget.ClickableSubtitleToolbar
import ru.sharipov.podcaster.f_best.di.BestScreenConfigurator
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class BestFragmentView : BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var presenter: BestFragmentPresenter

    @Inject
    lateinit var sh: BestStateHolder

    private val easyAdapter = PaginationableAdapter(R.layout.layout_best_podcast_row_skeleton){ presenter.loadMore() }
    private val podcastController = BestPodcastController { presenter.onPodcastClick(it) }

    override fun createConfigurator() = BestScreenConfigurator(arguments)

    override fun getScreenName(): String = "BestFragmentView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_best, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::render)
    }

    private fun initView() {
        best_podcasts_swr.setOnRefreshListener { presenter.onRefresh() }
        best_podcasts_pv.errorClickListener = { presenter.onErrorClick() }
        best_podcasts_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = easyAdapter
        }
        best_podcasts_toolbar.run {
            backClickListener = { presenter.onBackClick() }
            subtitleClickListener = { presenter.onRegionClick() }
        }
    }

    private fun render(state: BestState) {
        best_podcasts_toolbar.performIfChanged(state.genre.name, ClickableSubtitleToolbar::titleText::set)
        best_podcasts_toolbar.performIfChanged(state.region.name, ClickableSubtitleToolbar::subtitleText::set)
        best_podcasts_pv.performIfChanged(state.podcasts.placeholderState, PlaceholderStateView::setState)
        best_podcasts_swr.performIfChanged(state.podcasts.isSwrLoading, SwipeRefreshLayout::setRefreshing)
        best_podcasts_rv.performIfChanged(state.podcasts.data) { bundle ->
            bundle.safeGet { dataList, paginationState ->
                easyAdapter.setData(dataList, podcastController, paginationState)
            }
        }
    }
}