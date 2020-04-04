package ru.sharipov.podcaster.f_explore.genres

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class GenresPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: GenresReducer,
    private val podcastInteractor: PodcastInteractor
) : StatePresenter(dependency) {

    override fun onFirstLoad() {
        fetchGenres()
    }

    fun onGenreClick(genre: Genre) {
        // TODO переход к лучшим подкастам по жанру
    }

    fun onRetryClick() {
        fetchGenres()
    }

    private fun fetchGenres() {
        podcastInteractor.getGenres()
            .asRequest()
            .io()
            .subscribeDefault(reducer::onGenresRequest)
    }
}