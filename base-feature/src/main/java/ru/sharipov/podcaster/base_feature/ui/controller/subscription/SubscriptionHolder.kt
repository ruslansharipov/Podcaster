package ru.sharipov.podcaster.base_feature.ui.controller.subscription

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.base_feature.ui.extesions.distinctText
import ru.sharipov.podcaster.domain.Subscription
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

open class SubscriptionHolder<P : Subscription>(
    @LayoutRes layoutRes: Int,
    parent: ViewGroup,
    clickListener: (P) -> Unit
) : BindableViewHolder<P>(parent, layoutRes) {

    private val iconIv = itemView.findViewById<ImageView>(R.id.subscription_iv)
    private  val titleTv = itemView.findViewById<TextView>(R.id.subscription_title_tv)
    private val subtitleTv = itemView.findViewById<TextView>(R.id.subscription_subtitle_tv)
    private val clickableView = itemView.findViewById<View>(R.id.subscription_clickable)

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