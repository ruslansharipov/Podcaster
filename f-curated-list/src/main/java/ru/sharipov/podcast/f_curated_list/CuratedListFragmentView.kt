package ru.sharipov.podcast.f_curated_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import kotlinx.android.synthetic.main.fragment_curated_list.*
import ru.sharipov.podcast.f_curated_list.di.CuratedListScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.adapter.PaginationableAdapter
import ru.sharipov.podcaster.base_feature.ui.extesions.dpToPx
import ru.sharipov.podcaster.base_feature.ui.extesions.isSwrLoading
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.extesions.placeholderState
import ru.sharipov.podcaster.base_feature.ui.pagination.PaginationBundle
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderState
import ru.sharipov.podcaster.domain.CuratedItem
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class CuratedListFragmentView : BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var sh: CuratedListStateHolder

    @Inject
    lateinit var presenter: CuratedListPresenter

    private val easyAdapter = PaginationableAdapter(
        loadingIndicatorRes = R.layout.layout_curated_item_skeleton,
        onShowMoreListener = { presenter.loadMore() }
    )
    private val curatedItemController = CuratedItemController(
        allClickListener = { presenter.allClick(it) },
        podcastClickListener = { presenter.podcastClick(it) }
    )

    override fun createConfigurator() = CuratedListScreenConfigurator()

    override fun getScreenName(): String = "CuratedListFragmentView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_curated_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::render)
    }

    private fun initView() {
        curated_list_placeholder.errorClickListener = presenter::reload
        curated_list_swr.run {
            setProgressViewOffset(true, dpToPx(0), dpToPx(12))
            refreshes().bindTo(presenter::reload)
        }
        curated_list_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = easyAdapter
        }
    }

    private fun render(state: CuratedListState) {
        val curatedItems = state.curatedItems
        curated_list_swr.performIfChanged(curatedItems.isSwrLoading) { isRefreshing: Boolean ->
            this.isRefreshing = isRefreshing
        }
        curated_list_placeholder.performIfChanged(curatedItems.placeholderState) { placeholderState: PlaceholderState ->
            setState(placeholderState)
        }
        curated_list_rv.performIfChanged(curatedItems.data) { bundle: PaginationBundle<CuratedItem> ->
            bundle.safeGet { dataList, paginationState ->
                easyAdapter.setData(dataList, curatedItemController, paginationState)
            }
        }
    }
}