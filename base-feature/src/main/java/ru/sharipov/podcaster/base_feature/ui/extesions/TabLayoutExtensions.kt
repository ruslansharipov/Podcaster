package ru.sharipov.podcaster.base_feature.ui.extesions

import com.google.android.material.tabs.TabLayout

/**
 * @listener слушатель выбора табов
 */
fun TabLayout.addOnTabSelectedListener(listener: (tab: TabLayout.Tab) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

        override fun onTabReselected(tab: TabLayout.Tab?) {
            // без реализации
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            // без реализации
        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            listener(tab)
        }
    })
}
