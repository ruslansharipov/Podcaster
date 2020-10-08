package ru.sharipov.podcaster.f_subscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_subscriptions.*
import ru.sharipov.podcaster.base_feature.ui.controller.EpisodeController
import ru.sharipov.podcaster.base_feature.ui.controller.subscription.SubscriptionController
import ru.sharipov.podcaster.base_feature.ui.controller.subscription.SubscriptionControllerType
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderStateView
import ru.sharipov.podcaster.domain.PodcastFull
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.easyadapter.EasyAdapter
import javax.inject.Inject

class SubscriptionsFragmentView : BaseRxFragmentView() {

    @Inject
    lateinit var sh: SubscriptionsStateHolder

    @Inject
    lateinit var presenter: SubscriptionsPresenter

    private val episodesAdapter = EasyAdapter()
    private val episodeController = EpisodeController(
        clickListener = { presenter.onEpisodeClick(it) }
    )
    private val subscriptionsAdapter = EasyAdapter()
    private val subscriptionController = SubscriptionController(
            type = SubscriptionControllerType.LIST_ITEM,
            clickListener = { podcast: PodcastFull ->
                presenter.onSubscriptionClick(podcast)
            }
        )

    override fun createConfigurator() = SubscriptionsScreenConfigurator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_subscriptions, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::renderState)
    }

    private fun initView() {
        subscriptions_rv.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = subscriptionsAdapter
        }
        subscriptions_episodes_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = episodesAdapter
        }
    }

    private fun renderState(state: SubscriptionsState) {
        subscriptions_pv.performIfChanged(state.placeholderState, PlaceholderStateView::setState)
        subscriptions_rv.performIfChanged(state.subscriptions) {
            subscriptionsAdapter.setData(state.subscriptions, subscriptionController)
        }
        subscriptions_episodes_rv.performIfChanged(state.episodes.data){ episodes ->
            episodesAdapter.setData(episodes, episodeController)
        }
    }
}