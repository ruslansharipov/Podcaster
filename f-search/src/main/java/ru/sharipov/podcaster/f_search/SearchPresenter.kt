package ru.sharipov.podcaster.f_search

import io.reactivex.Observable
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.PodcastTypeAhead
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerScreen
class SearchPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: SearchReducer,
    private val podcastInteractor: PodcastInteractor
) : StatePresenter(dependency) {

    private companion object {
        const val DEBOUNCE_MS = 500L
    }

    fun subscribeOnQueryChanges(textChanges: Observable<String>) {
        textChanges
            .debounce(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
            .doOnNext(reducer::onQueryChanged)
            .switchMap { debouncedQuery: String ->
                podcastInteractor.getTypeAhead(
                    query = debouncedQuery,
                    showPodcasts = true,
                    showGenres = true
                )
            }
            .asRequest()
            .subscribeIoDefault(reducer::onTypeAheadRequest)
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

}
