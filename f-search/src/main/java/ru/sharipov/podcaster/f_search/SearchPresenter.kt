package ru.sharipov.podcaster.f_search

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.dismiss
import ru.sharipov.podcaster.base_feature.ui.navigation.SearchDialogRoute
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.PodcastTypeAhead
import ru.sharipov.podcaster.domain.TypeAhead
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.sharipov.podcaster.i_search.SearchInteractor
import ru.sharipov.podcaster.i_search.SearchResult
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerScreen
class SearchPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val route: SearchDialogRoute,
    private val sh: SearchStateHolder,
    private val reducer: SearchReducer,
    private val podcastInteractor: PodcastInteractor,
    private val searchInteractor: SearchInteractor,
    private val navigationExecutor: AppCommandExecutor
) : StatePresenter(dependency) {

    private companion object {
        const val DEBOUNCE_MS = 500L
    }

    private val searchQueryRelay = BehaviorRelay.create<String>()

    override fun onFirstLoad() {
        subscribeOnQueryChanges()
    }

    fun onQueryChanged(query: String) {
        searchQueryRelay.accept(query)
        reducer.onQueryChanged(query)
    }

    private fun subscribeOnQueryChanges() {
        searchQueryRelay
            .debounce(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
            .filter(String::isNotEmpty)
            .switchMap(::createTypeAheadObservable)
            .subscribeIoDefault(reducer::onTypeAheadRequest)
    }

    fun onSearchClick() {
        // TODO
    }

    fun retryClick() {
        createTypeAheadObservable(sh.value.input)
            .subscribeIoDefault(reducer::onTypeAheadRequest)
    }

    fun onGenreClick(genre: Genre) {
        searchInteractor.emitSearchResult(SearchResult.GenreResult(genre))
        dismiss()
    }

    fun onPodcastClick(podcast: PodcastTypeAhead) {
        searchInteractor.emitSearchResult(SearchResult.PodcastResult(podcast.toPodcastFull()))
        dismiss()
    }

    fun onCrearClick() {
        reducer.onClearClick()
    }

    fun onTermClick(term: String) {
        reducer.onTermClick(term)
    }

    fun onBackClick() {
        dismiss()
    }

    private fun createTypeAheadObservable(debouncedQuery: String): Observable<Request<TypeAhead>> {
        return podcastInteractor.getTypeAhead(
            query = debouncedQuery,
            showPodcasts = true,
            showGenres = true
        ).asRequest()
    }

    private fun dismiss() {
        navigationExecutor.dismiss(route)
    }
}
