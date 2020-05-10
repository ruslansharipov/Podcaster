package ru.sharipov.podcaster.f_subscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import javax.inject.Inject

class SubscriptionsFragmentView : BaseRxFragmentView() {

    @Inject
    lateinit var sh: SubscriptionsStateHolder

    @Inject
    lateinit var presenter: SubscriptionsPresenter

    override fun createConfigurator() = SubscriptionsScreenConfigurator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_subscriptions, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        sh.bindTo(::renderState)
    }

    private fun renderState(state: SubscriptionsState) {

    }
}