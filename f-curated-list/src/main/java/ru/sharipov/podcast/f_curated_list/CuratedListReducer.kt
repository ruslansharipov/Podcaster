package ru.sharipov.podcast.f_curated_list

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.pagination.PaginationBundle
import ru.sharipov.podcaster.domain.CuratedItem
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import javax.inject.Inject

data class CuratedListState(
    val curatedItems: RequestUi<PaginationBundle<CuratedItem>> = RequestUi()
)

@PerScreen
class CuratedListStateHolder @Inject constructor() : State<CuratedListState>(CuratedListState())

@PerScreen
class CuratedListReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: CuratedListStateHolder
) : StateReducer(dependency) {

    fun onCuratedLoaded(request: Request<DataList<CuratedItem>>, isSwr: Boolean = false) {
        sh.emitNewState {
            copy(
                curatedItems = mapPaginationDefault(request, curatedItems, isSwr)
            )
        }
    }

}