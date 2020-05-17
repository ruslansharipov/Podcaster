package ru.sharipov.podcaster.base_feature.ui.widget.inset.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import ru.sharipov.podcaster.base_feature.ui.widget.inset.InsetContainer
import ru.sharipov.podcaster.base_feature.ui.widget.inset.DefaultInsetContainer

/**
 * Контейнер, поглощающий insets и поставляющий информацию о них.
 * Наследуется от [FrameLayout]
 */
class InsetFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr), InsetContainer by DefaultInsetContainer(context)
