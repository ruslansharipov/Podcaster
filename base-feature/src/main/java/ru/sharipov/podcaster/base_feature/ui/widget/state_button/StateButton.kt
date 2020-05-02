package ru.sharipov.podcaster.base_feature.ui.widget.state_button

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_state_button.view.*
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.dpToPx

class StateButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    enum class State { PLAYING, PAUSED, LOADING }

    init {
        View.inflate(context, R.layout.view_state_button, this)
        radius = dpToPx(24).toFloat()
        elevation = 0f
    }

    fun setState(state: State) {
        val isLoading = state == State.LOADING
        state_btn_pv.isVisible = isLoading
        state_btn_ibtn.isVisible = !isLoading
        val buttonImageRes = if (state == State.PLAYING){
            R.drawable.ic_pause
        } else {
            R.drawable.ic_play
        }
        state_btn_ibtn.setImageResource(buttonImageRes)
    }
}