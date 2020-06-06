package ru.sharipov.podcast.f_curated_list

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_curated_carousel.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.distinctText
import ru.sharipov.podcaster.domain.CuratedItem
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class CuratedInfoController(
    private val allClickListener: (CuratedItem) -> Unit
) : BindableItemController<CuratedItem, CuratedInfoController.Holder>() {

    override fun getItemId(data: CuratedItem): String = data.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<CuratedItem>(parent, R.layout.list_item_curated_carousel) {

        private val domainTv = itemView.curated_domain_tv
        private val titleTv = itemView.curated_title_tv
        private val descriptionTv = itemView.curated_description_tv
        private val allBtn = itemView.curated_all_btn

        private var payload: CuratedItem? = null

        init {
            allBtn.setOnClickListener { payload?.let(allClickListener) }
        }

        override fun bind(data: CuratedItem) {
            payload = data

            domainTv.distinctText = data.sourceDomain
            titleTv.distinctText = data.title
            descriptionTv.distinctText = data.description
        }
    }

}