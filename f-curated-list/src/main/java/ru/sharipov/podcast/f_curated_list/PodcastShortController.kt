package ru.sharipov.podcast.f_curated_list

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_podcast_short.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.base_feature.ui.extesions.distinctText
import ru.sharipov.podcaster.base_feature.ui.imageLoader.PictureBinder
import ru.sharipov.podcaster.domain.PodcastShort
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class PodcastShortController(
    private val clickListener: (PodcastShort) -> Unit
) : BindableItemController<PodcastShort, PodcastShortController.Holder>() {

    override fun getItemId(data: PodcastShort): String = data.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<PodcastShort>(parent, R.layout.list_item_podcast_short), PictureBinder {

        private val iconIv = itemView.podcast_short_iv
        private val titleTv = itemView.podcast_short_title_tv
        private val subtitleTv = itemView.podcast_short_subtitle_tv
        private val clickableView = itemView.podcast_short_clickable

        private var payload: PodcastShort? = null

        init {
            clickableView.setOnClickListener { payload?.let(clickListener) }
        }

        override fun bind(data: PodcastShort) {
            payload = data

            titleTv.distinctText = data.title
            subtitleTv.distinctText = data.publisher
            iconIv.bindPicture(data.image)
        }
    }
}