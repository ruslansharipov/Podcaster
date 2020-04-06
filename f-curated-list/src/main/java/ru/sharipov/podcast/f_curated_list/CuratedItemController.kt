package ru.sharipov.podcast.f_curated_list

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_curated_carousel.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.distinctText
import ru.sharipov.podcaster.domain.CuratedItem
import ru.sharipov.podcaster.domain.PodcastShort
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class CuratedItemController(
    private val allClickListener: (CuratedItem) -> Unit,
    private val podcastClickListener: (PodcastShort) -> Unit
) : BindableItemController<CuratedItem, CuratedItemController.Holder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemId(data: CuratedItem): String = data.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<CuratedItem>(parent, R.layout.list_item_curated_carousel) {

        private val domainTv = itemView.curated_domain_tv
        private val titleTv = itemView.curated_title_tv
        private val descriptionTv = itemView.curated_description_tv
        private val allBtn = itemView.curated_all_btn
        private val podcastsRv = itemView.curated_podcasts_rv

        private val easyAdapter = EasyAdapter()
        private val podcastController = PodcastShortController(podcastClickListener)

        private var payload: CuratedItem? = null

        init {
            allBtn.setOnClickListener { payload?.let(allClickListener) }
            podcastsRv.run {
                setRecycledViewPool(viewPool)
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = easyAdapter
            }
        }

        override fun bind(data: CuratedItem) {
            payload = data

            domainTv.distinctText = data.sourceDomain
            titleTv.distinctText = data.title
            descriptionTv.distinctText = data.description
            easyAdapter.setData(data.podcasts, podcastController)
        }
    }

}