package ru.sharipov.podcast.f_curated_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import kotlinx.android.synthetic.main.fragment_curated_list.*
import ru.sharipov.podcast.f_curated_list.di.CuratedListScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.adapter.PaginationableAdapter
import ru.sharipov.podcaster.base_feature.ui.controller.subscription.SubscriptionController
import ru.sharipov.podcaster.base_feature.ui.controller.subscription.SubscriptionControllerType
import ru.sharipov.podcaster.base_feature.ui.extesions.dpToPx
import ru.sharipov.podcaster.base_feature.ui.extesions.isSwrLoading
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.extesions.placeholderState
import ru.sharipov.podcaster.base_feature.ui.pagination.PaginationBundle
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderStateView
import ru.sharipov.podcaster.domain.CuratedItem
import ru.sharipov.podcaster.domain.PodcastShort
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.item.BindableItem
import javax.inject.Inject

class CuratedListFragmentView : BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var sh: CuratedListStateHolder

    @Inject
    lateinit var presenter: CuratedListPresenter

    private val podcastClickListener: (PodcastShort) -> Unit = { presenter.podcastClick(it) }

    private val easyAdapter = PaginationableAdapter(
        loadingIndicatorRes = R.layout.layout_curated_item_skeleton,
        onShowMoreListener = { presenter.loadMore() }
    )
    private val curatedItemController = CuratedInfoController(
        allClickListener = { presenter.allClick(it) }
    )
    private val gridItemController = SubscriptionController(
        type = SubscriptionControllerType.GRID_ITEM,
        clickListener = podcastClickListener
    )

    private val fullItemController = SubscriptionController(
        type = SubscriptionControllerType.GRID_ITEM,
        clickListener = podcastClickListener
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
        curated_search_btn.setOnClickListener { presenter.onSearchClick() }
        curated_list_placeholder.errorClickListener = presenter::reload
        curated_list_swr.run {
            setProgressViewOffset(true, dpToPx(0), dpToPx(12))
            refreshes().bindTo(presenter::reload)
        }
        curated_list_rv.run {
            layoutManager = GridLayoutManager(context, 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val itemController = easyAdapter.getItem(position).itemController
                        val isGridItem = itemController == gridItemController
                        return if (isGridItem) 1 else 2
                    }
                }
            }
            adapter = easyAdapter
        }
    }

    private fun render(state: CuratedListState) {
        val curatedItems = state.curatedItems
        curated_list_swr.performIfChanged(
            curatedItems.isSwrLoading,
            SwipeRefreshLayout::setRefreshing
        )
        curated_list_placeholder.performIfChanged(
            curatedItems.placeholderState,
            PlaceholderStateView::setState
        )
        curated_list_rv.performIfChanged(curatedItems.data, { renderList(it) })
    }

    private fun renderList(bundle: PaginationBundle<CuratedItem>) {
        bundle.safeGet { dataList, paginationState ->
            val itemList = ItemList()
            dataList.forEach { item ->
                itemList.add(BindableItem(item, curatedItemController))

                val size = item.podcasts.size
                val hasEvenSize = size % 2 == 0

                val podcastItems = item.podcasts.mapIndexed { index, podcastShort ->
                    val isFirst = index == 0
                    val isFullWidth = !hasEvenSize && isFirst
                    val controller = if (isFullWidth) fullItemController else gridItemController
                    BindableItem(podcastShort, controller)
                }
                itemList.addAll(podcastItems)
            }

            easyAdapter.setItems(itemList, paginationState)
        }
    }
}