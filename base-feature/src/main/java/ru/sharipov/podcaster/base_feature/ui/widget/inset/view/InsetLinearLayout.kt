package ru.sharipov.podcaster.base_feature.ui.widget.inset.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import ru.sharipov.podcaster.base_feature.ui.widget.inset.DefaultInsetContainer
import ru.sharipov.podcaster.base_feature.ui.widget.inset.InsetContainer

class InsetLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : LinearLayout(context, attrs, defStyleAttr), InsetContainer by DefaultInsetContainer(context)