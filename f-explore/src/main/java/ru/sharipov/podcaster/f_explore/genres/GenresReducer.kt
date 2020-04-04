package ru.sharipov.podcaster.f_explore.genres

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.domain.Genre
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class GenresState(
    val genres: RequestUi<List<Genre>> = RequestUi()
)

@PerScreen
class GenresStateHolder @Inject constructor() : State<GenresState>(GenresState())

@PerScreen
class GenresReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: GenresStateHolder
) : StateReducer(dependency) {

    fun onGenresRequest(request: Request<List<Genre>>) {
        sh.emitNewState {
            copy(genres = mapRequestDefault(request, genres))
        }
    }
}