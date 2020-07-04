package ru.sharipov.podcaster.f_explore.genres

import androidx.fragment.app.Fragment
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class GenresFragmentRoute: FragmentRoute() {

    override fun getScreenClass(): Class<out Fragment>? {
        return GenresFragmentView::class.java
    }
}