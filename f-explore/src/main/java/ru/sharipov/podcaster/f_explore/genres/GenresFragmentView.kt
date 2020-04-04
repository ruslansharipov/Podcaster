package ru.sharipov.podcaster.f_explore.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_genres.*
import ru.sharipov.podcaster.base_feature.ui.extesions.performIfChanged
import ru.sharipov.podcaster.base_feature.ui.extesions.placeholderState
import ru.sharipov.podcaster.f_explore.R
import ru.sharipov.podcaster.f_explore.genres.di.GenresScreenConfigurator
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.easyadapter.EasyAdapter
import javax.inject.Inject

class GenresFragmentView : BaseRxFragmentView() {

    @Inject
    lateinit var sh: GenresStateHolder

    @Inject
    lateinit var presenter: GenresPresenter

    private val easyAdapter = EasyAdapter()
    private val genreController = GenreController { presenter.onGenreClick(it) }

    override fun createConfigurator() = GenresScreenConfigurator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_genres, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::render)
    }

    private fun initView() {
        genres_rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = easyAdapter
        }
        genres_placeholder.errorClickListener = { presenter.onRetryClick() }
    }

    private fun render(state: GenresState) {
        genres_placeholder.performIfChanged(state.genres.placeholderState){ placeholderState ->
            setState(placeholderState)
        }
        genres_rv.performIfChanged(state.genres.data){ genres ->
            easyAdapter.setData(genres, genreController)
        }
    }
}