package ru.sharipov.podcaster.f_search

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.domain.TypeAhead
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import javax.inject.Inject

data class SearchState(
    val input: String = EMPTY_STRING,
    val typeAhead: RequestUi<TypeAhead> = RequestUi(),
    val typeAheadList: List<TypeAheadItem> = emptyList()
) {
    val isClearBtnVisible: Boolean = input.isNotEmpty()
}

@PerScreen
class SearchStateHolder @Inject constructor() : State<SearchState>(SearchState())

@PerScreen
class SearchReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: SearchStateHolder
) : StateReducer(dependency) {

    fun onQueryChanged(query: String) {
        sh.emitNewState {
            copy(
                input = query,
                typeAheadList = if (query.isEmpty()) emptyList() else typeAheadList
            )
        }
    }

    fun onClearClick() {
        sh.emitNewState {
            copy(
                input = EMPTY_STRING,
                typeAheadList = emptyList()
            )
        }
    }

    fun onTypeAheadRequest(request: Request<TypeAhead>) {
        sh.emitNewState {
            copy(
                typeAhead = mapRequestDefault(request, typeAhead, typeAheadList.isNotEmpty()),
                typeAheadList = if (request is Request.Success) {
                    createTypeAheadList(request.data)
                } else {
                    typeAheadList
                }
            )
        }
    }

    private fun createTypeAheadList(data: TypeAhead): List<TypeAheadItem> {
        return mutableListOf<TypeAheadItem>().apply {
            if (data.terms.isNotEmpty()){
                add(TypeAheadItem.TitleItem(R.string.search_terms_title))
                addAll(data.terms.map(TypeAheadItem::TermItem))
            }
            if (data.podcasts.isNotEmpty()){
                add(TypeAheadItem.TitleItem(R.string.search_podcasts_title))
                addAll(data.podcasts.map(TypeAheadItem::PodcastItem))
            }
            if (data.genres.isNotEmpty()){
                add(TypeAheadItem.TitleItem(R.string.search_genres_title))
                addAll(data.genres.map(TypeAheadItem::GenreItem))
            }
        }
    }

    fun onTermClick(term: String) {
        sh.emitNewState {
            copy(input = term)
        }
    }
}
