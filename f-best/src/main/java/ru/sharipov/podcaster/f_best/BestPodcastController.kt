package ru.sharipov.podcaster.f_best

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_best_podcast.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.domain.Podcast
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class BestPodcastController(
    private val clickListener: (Podcast) -> Unit
) : BindableItemController<Podcast, BestPodcastController.Holder>() {

    override fun getItemId(data: Podcast): String = data.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<Podcast>(parent, R.layout.list_item_best_podcast) {

        private val titleTv = itemView.best_podcast_title_tv
        private val descriptionTv = itemView.best_podcast_description_tv
        private val publisherTv = itemView.best_podcast_publisher_tv
        private val podcastIv = itemView.best_podcast_iv
        private val clickableView = itemView.best_podcast_clickable

        private var payload: Podcast? = null

        init {
            clickableView.setOnClickListener { payload?.let(clickListener) }
        }

        override fun bind(data: Podcast) {
            payload = data

            titleTv.text = data.title
            descriptionTv.text = data.description
            publisherTv.text = data.publisher
            podcastIv.bindPicture(data.image)
        }
    }
}