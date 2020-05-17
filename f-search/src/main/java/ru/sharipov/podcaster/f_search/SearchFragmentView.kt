package ru.sharipov.podcaster.f_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import ru.sharipov.podcaster.base_feature.ui.controller.GenreController
import ru.sharipov.podcaster.base_feature.ui.extesions.*
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderState
import ru.sharipov.podcaster.f_search.controller.PodcastItemController
import ru.sharipov.podcaster.f_search.controller.TermItemController
import ru.sharipov.podcaster.f_search.controller.TitleItemController
import ru.sharipov.podcaster.f_search.di.SearchScreenConfigurator
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.item.BindableItem
import ru.surfstudio.android.utilktx.ktx.ui.view.hideSoftKeyboard
import ru.surfstudio.android.utilktx.ktx.ui.view.showKeyboard
import javax.inject.Inject

class SearchFragmentView : BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var sh: SearchStateHolder

    @Inject
    lateinit var presenter: SearchPresenter

    private val easyAdapter = EasyAdapter()
    private val titleController = TitleItemController()
    private val termController = TermItemController { presenter.onTermClick(it) }
    private val genreController = GenreController { presenter.onGenreClick(it) }
    private val podcastController = PodcastItemController { presenter.onPodcastClick(it) }

    override fun getScreenName(): String = "SearchFragmentView"

    override fun createConfigurator() = SearchScreenConfigurator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?, viewRecreated: Boolean) {
        initView()
        sh.bindTo(::render)
    }

    private fun initView() {
        search_list_rv.run {
            layoutManager = FlexboxLayoutManager(context)
            adapter = easyAdapter
        }
        search_pv.errorClickListener = { presenter.retryClick() }
        search_clear_iv.setOnClickListener { presenter.onCrearClick() }
        search_et.textChangesStringSkipFirst().bindTo(presenter::onQueryChanged)
        search_et.searchActions().bindTo(presenter::onSearchClick)
    }

    private fun render(state: SearchState) {
        search_et.distinctText = state.input
        search_clear_iv.isVisible = state.isClearBtnVisible
        search_list_rv.performIfChanged(state.typeAheadList) { typeAheadItems: List<TypeAheadItem> ->
            val items = typeAheadItems.map { item: TypeAheadItem ->
                when (item) {
                    is TypeAheadItem.TitleItem -> BindableItem(item, titleController)
                    is TypeAheadItem.TermItem -> BindableItem(item, termController)
                    is TypeAheadItem.GenreItem -> BindableItem(item.genre, genreController)
                    is TypeAheadItem.PodcastItem -> BindableItem(item, podcastController)
                }
            }
            easyAdapter.setItems(ItemList(items))
        }
        search_pv.performIfChanged(state.typeAhead.placeholderState){ placeholderState: PlaceholderState ->
            setState(placeholderState)
        }
    }

    override fun onPause() {
        search_et.hideSoftKeyboard()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        search_et.showKeyboard()
    }
}