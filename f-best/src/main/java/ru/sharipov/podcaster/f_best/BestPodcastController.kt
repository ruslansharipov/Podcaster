package ru.sharipov.podcaster.f_best

import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.list_item_best_podcast.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.domain.PodcastFull
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class BestPodcastController(
    private val clickListener: (PodcastFull) -> Unit
) : BindableItemController<PodcastFull, BestPodcastController.Holder>() {

    override fun getItemId(data: PodcastFull): String = data.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<PodcastFull>(parent, R.layout.list_item_best_podcast) {

        private val titleTv = itemView.best_podcast_title_tv
        private val descriptionTv = itemView.best_podcast_description_tv
        private val publisherTv = itemView.best_podcast_publisher_tv
        private val podcastIv = itemView.best_podcast_iv
        private val clickableView = itemView.best_podcast_clickable

        private var payload: PodcastFull? = null

        init {
            clickableView.setOnClickListener { payload?.let(clickListener) }
        }

        override fun bind(data: PodcastFull) {
            payload = data

            titleTv.text = data.title
            publisherTv.text = data.publisher
            descriptionTv.text = HtmlCompat.fromHtml(data.description, HtmlCompat.FROM_HTML_MODE_COMPACT)

            podcastIv.bindPicture(data.image)
        }
    }
}