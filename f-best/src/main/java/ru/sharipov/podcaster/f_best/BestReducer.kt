package ru.sharipov.podcaster.f_best

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.BestFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.pagination.PaginationBundle
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.Podcast
import ru.sharipov.podcaster.domain.Region
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import javax.inject.Inject

data class BestState(
    val genre: Genre,
    val region: Region,
    val podcasts: RequestUi<PaginationBundle<Podcast>> = RequestUi()
)

@PerScreen
class BestStateHolder @Inject constructor(route: BestFragmentRoute) : State<BestState>(
    BestState(
        genre = route.genre,
        region = route.region
    )
)

@PerScreen
class BestReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: BestStateHolder
) : StateReducer(dependency) {

    fun onRegionChanged(newRegion: Region) {
        sh.emitNewState {
            copy(region = newRegion)
        }
    }

    fun onPodcastsLoaded(
        request: Request<DataList<Podcast>>,
        isSwr: Boolean
    ) {
        sh.emitNewState {
            copy(
                podcasts = mapPaginationDefault(request, podcasts, isSwr)
            )
        }
    }

}
