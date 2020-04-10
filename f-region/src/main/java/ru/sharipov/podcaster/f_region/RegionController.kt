package ru.sharipov.podcaster.f_region

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_region.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.domain.Region
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class RegionController(
    private val clickListener: (Region) -> Unit
): BindableItemController<SelectableRegion, RegionController.Holder>() {

    override fun getItemId(data: SelectableRegion): String = data.region.hashCode().toString()

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup):
        BindableViewHolder<SelectableRegion>(parent, R.layout.list_item_region) {

        private val titleTv = itemView.region_title_tv
        private val flagIv = itemView.region_flag_iv

        private var payload: Region? = null

        init {
            itemView.setOnClickListener { payload?.let(clickListener) }
        }

        override fun bind(data: SelectableRegion) {
            payload = data.region

            titleTv.text = data.region.name
            flagIv.bindPicture(data.region.flagUrl)
        }
    }
}