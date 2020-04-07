package ru.sharipov.podcaster.f_search

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.bus.InsetsInteractor
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.PodcastTypeAhead
import ru.sharipov.podcaster.domain.TypeAhead
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.Request
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerScreen
class SearchPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val sh: SearchStateHolder,
    private val reducer: SearchReducer,
    private val podcastInteractor: PodcastInteractor,
    private val insetsInteractor: InsetsInteractor
) : StatePresenter(dependency) {

    private companion object {
        const val DEBOUNCE_MS = 500L
    }

    private val searchQueryRelay = BehaviorRelay.create<String>()

    override fun onFirstLoad() {
        subscribeOnQueryChanges()
        subscribeOnInsetChanges()
    }

    private fun subscribeOnInsetChanges() {
        insetsInteractor
            .observeInsets()
            .subscribeDefault(reducer::onNewInsets)
    }

    fun onQueryChanged(query: String) {
        searchQueryRelay.accept(query)
    }

    private fun subscribeOnQueryChanges() {
        searchQueryRelay
            .debounce(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
            .doOnNext(reducer::onQueryChanged)
            .filter(String::isNotEmpty)
            .switchMap(::createTypeAheadObservable)
            .subscribeDefault(reducer::onTypeAheadRequest)
    }

    fun retryClick() {
        createTypeAheadObservable(sh.value.input)
            .subscribeDefault(reducer::onTypeAheadRequest)
    }

    fun onGenreClick(genre: Genre) {
        // TODO
    }

    fun onPodcastClick(podcast: PodcastTypeAhead) {
        // TODO
    }

    fun onCrearClick() {
        reducer.onClearClick()
    }

    fun onTermClick(term: String) {
        reducer.onTermClick(term)
    }

    private fun createTypeAheadObservable(debouncedQuery: String): Observable<Request<TypeAhead>> {
        return podcastInteractor.getTypeAhead(
            query = debouncedQuery,
            showPodcasts = true,
            showGenres = true
        )
            .asRequest()
            .io()
    }

}
