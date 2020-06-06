package ru.sharipov.podcaster.base_feature.ui.controller.subscription

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.controller.subscription.SubscriptionControllerType.*
import ru.sharipov.podcaster.domain.Subscription
import ru.surfstudio.android.easyadapter.controller.BindableItemController

class SubscriptionController<P : Subscription>(
    private val type: SubscriptionControllerType,
    private val clickListener: (P) -> Unit
) : BindableItemController<P, SubscriptionHolder<P>>() {

    @LayoutRes
    private val layoutRes: Int = when(type){
        FULL_SPAN -> R.layout.list_item_full_subscription
        LIST_ITEM -> R.layout.list_item_list_subscription
        GRID_ITEM -> R.layout.list_item_grid_subscription
    }

    override fun getItemId(data: P): String {
        return data.id
    }

    override fun createViewHolder(parent: ViewGroup): SubscriptionHolder<P> {
        return SubscriptionHolder(layoutRes, parent, clickListener)
    }
}