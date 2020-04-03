package ru.sharipov.podcaster.f_explore.curated

import androidx.fragment.app.Fragment
import ru.surfstudio.android.core.ui.navigation.fragment.route.FragmentRoute

class CuratedFragmentRoute: FragmentRoute() {

    override fun getFragmentClass(): Class<out Fragment> {
        return CuratedFragmentView::class.java
    }
}