package ru.sharipov.podcaster.base_feature.ui.controller

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_episode.view.*
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class EpisodeController(
    private val isFullEpisode: Boolean = false,
    private val clickListener: (Episode) -> Unit
) : BindableItemController<Episode, EpisodeController.Holder>() {
    override fun getItemId(data: Episode): String = data.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<Episode>(parent, R.layout.list_item_episode) {

        private val episodeView = itemView.list_item_episode

        private var payload: Episode? = null

        init {
            itemView.setOnClickListener { payload?.let(clickListener) }
        }

        override fun bind(data: Episode) {
            payload = data
            episodeView.setEpisode(data, isFullEpisode)
        }
    }


}
