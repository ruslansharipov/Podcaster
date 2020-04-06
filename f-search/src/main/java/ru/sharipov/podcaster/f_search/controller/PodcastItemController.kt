package ru.sharipov.podcaster.f_search.controller

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_typeahead_podcast.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.base_feature.ui.extesions.distinctText
import ru.sharipov.podcaster.domain.PodcastTypeAhead
import ru.sharipov.podcaster.f_search.R
import ru.sharipov.podcaster.f_search.TypeAheadItem
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class PodcastItemController(
    private val clickListener: (PodcastTypeAhead) -> Unit
) : BindableItemController<TypeAheadItem.PodcastItem, PodcastItemController.Holder>() {

    override fun getItemId(data: TypeAheadItem.PodcastItem): String = data.toString()

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<TypeAheadItem.PodcastItem>(parent, R.layout.list_item_typeahead_podcast) {

        private val titleTv = itemView.type_ahead_podcast_title_tv
        private val publisherTv = itemView.type_ahead_podcast_publisher_tv
        private val iconIv = itemView.type_ahead_podcast_icon_iv

        private var payload: PodcastTypeAhead? = null

        init {
            itemView.setOnClickListener { payload?.let(clickListener) }
        }

        override fun bind(data: TypeAheadItem.PodcastItem) {
            payload = data.podcast
            payload?.run {
                titleTv.distinctText = titleHighlighted
                publisherTv.distinctText = publisherHighlighted
                iconIv.bindPicture(image)
            }
        }
    }
}