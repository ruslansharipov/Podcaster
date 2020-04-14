package ru.sharipov.podcaster.f_podcast_episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_episodes.*
import ru.sharipov.podcaster.base.datalist_date.MergeList
import ru.sharipov.podcaster.base_feature.ui.adapter.PaginationableAdapter
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.extesions.placeholderState
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderStateView
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import javax.inject.Inject

class EpisodesFragmentView : BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var sh: EpisodesStateHolder

    @Inject
    lateinit var presenter: EpisodesPresenter

    private val easyAdapter = PaginationableAdapter(
        loadingIndicatorRes = R.layout.layout_episodes_item_skeleton,
        onShowMoreListener = { presenter.onShowMore() }
    )
    private val episodeController = EpisodeController { presenter.onEpisodeClick(it) }

    override fun getScreenName(): String = "EpisodesFragmentView"

    override fun createConfigurator() = EpisodesScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_episodes, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::renderState)
    }

    private fun initView() {
        episodes_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = easyAdapter
        }
        episodes_pv.errorClickListener = { presenter.onErrorClick() }
    }

    private fun renderState(state: EpisodesState) {
        episodes_pv.performIfChanged(
            state.episodes.placeholderState,
            PlaceholderStateView::setState
        )
        episodes_rv.performIfChanged(state.episodes.data) { mergeBundle ->
            mergeBundle.safeGet { mergeList: MergeList<Episode>, paginationState: PaginationState ->
                easyAdapter.setData(mergeList, episodeController, paginationState)
            }
        }
    }
}