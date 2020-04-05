package ru.sharipov.podcast.f_curated_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.sharipov.podcast.f_curated_list.di.CuratedListScreenConfigurator
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import javax.inject.Inject

class CuratedListFragmentView : BaseRxFragmentView(), CrossFeatureFragment {

    @Inject
    lateinit var sh: CuratedListStateHolder

    @Inject
    lateinit var presenter: CuratedListPresenter

    override fun createConfigurator() = CuratedListScreenConfigurator()

    override fun getScreenName(): String = "CuratedListFragmentView"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_curated_list, container, false)
}