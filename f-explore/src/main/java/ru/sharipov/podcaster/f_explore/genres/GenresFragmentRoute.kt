package ru.sharipov.podcaster.f_explore.genres

import androidx.fragment.app.Fragment
import ru.surfstudio.android.core.ui.navigation.fragment.route.FragmentRoute

class GenresFragmentRoute: FragmentRoute() {

    override fun getFragmentClass(): Class<out Fragment> {
        return GenresFragmentView::class.java
    }
}