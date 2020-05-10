package ru.sharipov.podcaster.base_feature.ui.controller

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_subscription.view.*
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.base_feature.ui.extesions.distinctText
import ru.sharipov.podcaster.base_feature.ui.imageLoader.PictureBinder
import ru.sharipov.podcaster.domain.Subscription
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class SubscriptionController<P : Subscription>(
    private val clickListener: (P) -> Unit
) : BindableItemController<P, SubscriptionController<P>.Holder>() {

    override fun getItemId(data: P): String = data.id

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<P>(parent, R.layout.list_item_subscription), PictureBinder {

        private val iconIv = itemView.subscription_iv
        private val titleTv = itemView.subscription_title_tv
        private val subtitleTv = itemView.subscription_subtitle_tv
        private val clickableView = itemView.subscription_clickable

        private var payload: P? = null

        init {
            clickableView.setOnClickListener { payload?.let(clickListener) }
        }

        override fun bind(data: P) {
            payload = data

            titleTv.distinctText = data.title
            subtitleTv.distinctText = data.publisher
            iconIv.bindPicture(data.image)
        }
    }
}