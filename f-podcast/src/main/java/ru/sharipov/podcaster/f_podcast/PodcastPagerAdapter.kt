package ru.sharipov.podcaster.f_podcast

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodesFragmentRoute

class PodcastPagerAdapter(
    fm: FragmentManager,
    private val podcastId: String
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // TODO добавить фрагмент Details
        return EpisodesFragmentRoute(podcastId).createFragment()
    }

    override fun getCount(): Int = 2

}