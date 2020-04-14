package ru.sharipov.podcaster.f_podcast_episodes

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_episode_short.view.*
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class EpisodeController(
    private val clickListener: (Episode) -> Unit
) : BindableItemController<Episode, EpisodeController.Holder>() {
    override fun getItemId(data: Episode): String = data.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<Episode>(parent, R.layout.list_item_episode_short) {

        private val titleTv = itemView.episode_title_tv

        private var payload: Episode? = null

        override fun bind(data: Episode) {
            titleTv.text = data.title
        }
    }


}
