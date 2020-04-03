package ru.sharipov.podcaster.f_explore.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.sharipov.podcaster.f_explore.R
import ru.surfstudio.android.core.mvp.binding.rx.ui.BaseRxFragmentView

class GenresFragmentView: BaseRxFragmentView() {

    override fun createConfigurator() = GenresScreenConfigurator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_genres, container, false)
}