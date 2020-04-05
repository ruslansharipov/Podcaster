package ru.sharipov.podcaster.f_main.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_bottom_tab.view.*
import ru.sharipov.podcaster.f_main.MainTabType
import ru.sharipov.podcaster.f_main.R

class BottomTabView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val tabButtonsMap: Map<MainTabType, ImageButton>
    private val selectedTabRelay = BehaviorRelay.create<MainTabType>()

    val selectedTabObservable: Observable<MainTabType> = selectedTabRelay.share()

    var selectedTabType: MainTabType = MainTabType.EXPLORE
        set(value) {
            field = value
            update()
        }

    init {
        View.inflate(context, R.layout.view_bottom_tab, this)

        orientation = HORIZONTAL

        tabButtonsMap = hashMapOf<MainTabType, ImageButton>(
            MainTabType.EXPLORE to bottom_tab_explore_btn,
            MainTabType.SEARCH to bottom_bar_search_btn,
            MainTabType.FEED to bottom_tab_feed_btn,
            MainTabType.PLAYLIST to bottom_bar_playlist_btn
        )

        initListeners()
        update()
    }

    private fun initListeners() {
        tabButtonsMap.forEach { (type, btn) ->
            btn.setOnClickListener { selectedTabRelay.accept(type) }
        }
    }

    /**
     * Перерисовка виджета.
     */
    private fun update() {
        updateSelection()
    }

    /**
     * Установка нажатого состояния выбранного таба.
     */
    private fun updateSelection() {
        checkSelectedTab()
        uncheckOtherTabs()
    }

    private fun checkSelectedTab() {
        tabButtonsMap[selectedTabType]?.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent))
    }

    private fun uncheckOtherTabs() {
        tabButtonsMap.keys
            .filter { it != selectedTabType }
            .forEach {
                tabButtonsMap[it]?.setColorFilter(ContextCompat.getColor(context, R.color.black))
            }
    }
}