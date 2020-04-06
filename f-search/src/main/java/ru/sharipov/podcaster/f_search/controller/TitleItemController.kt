package ru.sharipov.podcaster.f_search.controller

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_typeahead_title.view.*
import ru.sharipov.podcaster.f_search.R
import ru.sharipov.podcaster.f_search.TypeAheadItem
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class TitleItemController :
    BindableItemController<TypeAheadItem.TitleItem, TitleItemController.Holder>() {

    override fun getItemId(data: TypeAheadItem.TitleItem): String = data.toString()

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<TypeAheadItem.TitleItem>(parent, R.layout.list_item_typeahead_title) {

        private val titleTv = itemView.type_ahead_title_tv

        override fun bind(data: TypeAheadItem.TitleItem) {
            titleTv.setText(data.titleRes)
        }
    }
}