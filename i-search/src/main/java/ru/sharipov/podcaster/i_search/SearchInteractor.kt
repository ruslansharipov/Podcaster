package ru.sharipov.podcaster.i_search

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Interactor for observing the results of search
 */
@PerApplication
class SearchInteractor @Inject constructor() {

    private val searchResultRelay = PublishRelay.create<SearchResult>()

    /**
     * Emits [result]
     */
    fun emitSearchResult(result: SearchResult){
        searchResultRelay.accept(result)
    }

    /**
     * Observe the results of search
     */
    fun observeSearchResult() : Observable<SearchResult> {
        return searchResultRelay.hide()
    }
}