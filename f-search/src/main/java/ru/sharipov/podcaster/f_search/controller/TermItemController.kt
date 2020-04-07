package ru.sharipov.podcaster.f_search.controller

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_typeahead_term.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.distinctText
import ru.sharipov.podcaster.f_search.R
import ru.sharipov.podcaster.f_search.TypeAheadItem
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class TermItemController(
    private val clickListener: (String) -> Unit
) : BindableItemController<TypeAheadItem.TermItem, TermItemController.Holder>() {

    override fun getItemId(data: TypeAheadItem.TermItem): String = data.toString()

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<TypeAheadItem.TermItem>(parent, R.layout.list_item_typeahead_term) {

        private val titleTv = itemView.type_ahead_term_tv
        private var payload: String? = null

        init {
            itemView.setOnClickListener { payload?.let(clickListener) }
        }

        override fun bind(data: TypeAheadItem.TermItem) {
            payload = data.term
            titleTv.distinctText = data.term
        }
    }
}