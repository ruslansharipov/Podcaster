package ru.sharipov.podcaster.f_subscription

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.domain.PodcastFull
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class SubscriptionsState(
    val subscriptions: List<PodcastFull> = emptyList()
)

@PerScreen
class SubscriptionsStateHolder @Inject constructor() : State<SubscriptionsState>(SubscriptionsState())

@PerScreen
class SubscriptionsReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: SubscriptionsStateHolder
): StateReducer(dependency) {

    fun onSubscriptionChanged(subscriptions: List<PodcastFull>) {
        sh.emitNewState {
            copy(
                subscriptions = subscriptions
            )
        }
    }

}
