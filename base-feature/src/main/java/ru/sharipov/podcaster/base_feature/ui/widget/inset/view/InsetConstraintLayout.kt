package ru.sharipov.podcaster.base_feature.ui.widget.inset.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import ru.sharipov.podcaster.base_feature.ui.widget.inset.InsetContainer
import ru.sharipov.podcaster.base_feature.ui.widget.inset.DefaultInsetContainer

/**
 * Контейнер, поглощающий insets и поставляющий информацию о них.
 * Наследуется от [ConstraintLayout]
 */
class InsetConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : ConstraintLayout(context, attrs, defStyleAttr), InsetContainer by DefaultInsetContainer(context)
