package ru.sharipov.podcaster.base_feature.ui.adapter

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.placeholder.AppShimmer
import ru.surfstudio.android.easyadapter.pagination.BasePaginationableAdapter
import ru.surfstudio.android.easyadapter.pagination.PaginationState

/**
 * Класс адаптера с поддержкой пагинации на основе EasyAdapter
 *
 * @param loadingIndicatorRes ресурс для инфлейта вьюхи, показывающей состояние загрузки списка
 * @param onShowMoreListener лиснер, вызываемый, когда необходимо подгрузить следуюущю пачку элементов
 */
open class PaginationableAdapter(
    loadingIndicatorRes: Int,
    onShowMoreListener: () -> Unit
) : BasePaginationableAdapter() {

    protected var paginationFooterItemController: PaginationFooterItemController? = null

    init {
        setOnShowMoreListener(onShowMoreListener)
        paginationFooterItemController?.loadingIndicatorRes = loadingIndicatorRes
    }

    override fun getPaginationFooterController(): BasePaginationFooterController<*> {
        if (paginationFooterItemController == null)
            paginationFooterItemController = PaginationFooterItemController()
        return paginationFooterItemController!!
    }

    protected class PaginationFooterItemController :
        BasePaginationFooterController<PaginationFooterItemController.Holder>() {

        var loadingIndicatorRes: Int = 0

        override fun createViewHolder(parent: ViewGroup, listener: OnShowMoreListener): Holder {
            return Holder(parent, listener)
        }

        inner class Holder(
            parent: ViewGroup,
            listener: OnShowMoreListener
        ) : BasePaginationFooterHolder(parent, R.layout.layout_pagination_footer) {

            private val loadingIndicator: View
            private val showMoreTv: TextView

            init {
                View.inflate(itemView.context, loadingIndicatorRes, itemView as ViewGroup)
                showMoreTv = itemView[0] as TextView
                loadingIndicator = itemView[1]

                showMoreTv.setOnClickListener { listener.onShowMore() }
                loadingIndicator.visibility = GONE
                showMoreTv.visibility = GONE
            }

            override fun bind(state: PaginationState) {

                // для пагинации на StaggeredGrid
                if (itemView.layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                    itemView.updateLayoutParams<StaggeredGridLayoutManager.LayoutParams> {
                        isFullSpan = true
                    }
                }
                loadingIndicator.isVisible = state == PaginationState.READY
                (loadingIndicator as? AppShimmer)?.isLoading = state == PaginationState.READY
                showMoreTv.isVisible = state == PaginationState.ERROR
            }
        }
    }
}
