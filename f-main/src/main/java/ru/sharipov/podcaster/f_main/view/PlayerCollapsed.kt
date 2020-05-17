package ru.sharipov.podcaster.f_main.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import ru.sharipov.podcaster.f_main.R

class PlayerCollapsed @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_player_collapsed, this)
        setBackgroundResource(R.drawable.bg_selectable_item)
        isClickable = true
        isFocusable = true
    }


}