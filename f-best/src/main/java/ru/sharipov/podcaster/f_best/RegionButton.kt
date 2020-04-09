package ru.sharipov.podcaster.f_best

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.view_region_button.view.*
import ru.sharipov.podcaster.base_feature.ui.extesions.bindPicture
import ru.sharipov.podcaster.base_feature.ui.extesions.dpToPx
import ru.sharipov.podcaster.domain.Region

class RegionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_region_button, this)
        radius = dpToPx(24).toFloat()
    }

    fun setRegion(region: Region) {
        region_btn_tv.text = region.name
        region_btn_iv.bindPicture(region.flagUrl)
    }

}