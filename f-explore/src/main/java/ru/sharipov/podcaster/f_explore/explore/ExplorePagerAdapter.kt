package ru.sharipov.podcaster.f_explore.explore

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.sharipov.podcaster.base_feature.ui.navigation.BestFragmentRoute
import ru.sharipov.podcaster.f_explore.R
import ru.sharipov.podcaster.base_feature.ui.navigation.CuratedListFragmentRoute
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.Region

@SuppressLint("WrongConstant")
class ExplorePagerAdapter(
    fm: FragmentManager,
    private val resources: Resources
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private companion object {
        const val PAGE_COUNT = 2
    }

    override fun getItem(position: Int): Fragment {
        val route = if (position == 0) {
            CuratedListFragmentRoute()
        } else {
            BestFragmentRoute(Genre())
        }
        return route.createFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val titleRes = if (position == 0){
            R.string.explore_curated_tab
        } else {
            R.string.explore_best_tab
        }
        return resources.getString(titleRes)
    }

    override fun getCount(): Int = PAGE_COUNT
}