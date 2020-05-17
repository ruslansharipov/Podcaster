package ru.sharipov.podcaster.base_feature.ui.widget.inset.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout
import ru.sharipov.podcaster.base_feature.ui.widget.inset.DefaultInsetContainer
import ru.sharipov.podcaster.base_feature.ui.widget.inset.InsetContainer

class InsetMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr), InsetContainer by DefaultInsetContainer(context)