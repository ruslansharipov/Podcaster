package ru.sharipov.podcaster.base_feature.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.layout_clickable_subtitle_toolbar.view.*
import ru.sharipov.podcaster.base_feature.R
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

/**
 * Туллбар с отображением кликабельного подзаголовка под заголовком.
 * Имеет кнопки закрытия экрана и возвращения на предыдущий.
 */
class ClickableSubtitleToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {

    var titleText: String = EMPTY_STRING
        set(value) {
            field = value
            clickable_subtitle_toolbar_title_tv.text = value
        }

    var subtitleText: String = EMPTY_STRING
        set(value) {
            field = value
            val isVisible = value.isNotEmpty()
            clickable_subtitle_toolbar_subtitle_tv.isVisible = isVisible
            clickable_subtitle_toolbar_subtitle_tv.text = value
        }

    var isBackVisible: Boolean = true
        set(value) {
            field = value
            clickable_subtitle_toolbar_back_btn.isVisible = value
        }

    var isCloseVisible: Boolean = true
        set(value) {
            field = value
            clickable_subtitle_toolbar_close_btn.isVisible = value
        }

    var isCityEnabled: Boolean = true
        set(value) {
            field = value
            clickable_subtitle_toolbar_subtitle_tv.isEnabled = value
        }

    var backClickListener: () -> Unit = {}
    var closeClickListener: () -> Unit = {}
    var subtitleClickListener: () -> Unit = {}

    init {
        inflate(context, R.layout.layout_clickable_subtitle_toolbar, this)
        obtainAttributes(attrs)
        initListeners()
    }

    private fun obtainAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickableSubtitleToolbar)
        titleText = typedArray.getString(R.styleable.ClickableSubtitleToolbar_title) ?: EMPTY_STRING
        isBackVisible = typedArray.getBoolean(R.styleable.ClickableSubtitleToolbar_back_visible, true)
        isCloseVisible = typedArray.getBoolean(R.styleable.ClickableSubtitleToolbar_close_visible, false)
        typedArray.recycle()
    }

    private fun initListeners() {
        clickable_subtitle_toolbar_back_btn.setOnClickListener { backClickListener() }
        clickable_subtitle_toolbar_close_btn.setOnClickListener { closeClickListener() }
        clickable_subtitle_toolbar_subtitle_tv.setOnClickListener { subtitleClickListener() }
    }
}
