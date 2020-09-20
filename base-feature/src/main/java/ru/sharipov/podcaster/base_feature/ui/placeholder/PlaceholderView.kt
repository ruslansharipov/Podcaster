package ru.sharipov.podcaster.base_feature.ui.placeholder

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import kotlinx.android.synthetic.main.view_placeholder.view.*
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.color
import ru.sharipov.podcaster.base_feature.ui.extesions.dpToPx
import ru.surfstudio.android.core.mvi.impls.ui.dialog.standard.EMPTY_RES
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.surfstudio.android.utilktx.ktx.ui.view.setTextAppearanceStyle
import ru.surfstudio.android.utilktx.ktx.ui.view.setTextOrGone

/**
 * View базового плейсхолдера для экранов
 */
class PlaceholderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : LinearLayout(context, attrs, defStyleAttr) {

    var title: CharSequence = EMPTY_STRING
        set(value) {
            field = value
            placeholder_title_tv.isVisible = value.isNotBlank()
            placeholder_title_tv.text = value
        }

    var subtitle: CharSequence = EMPTY_STRING
        set(value) {
            field = value
            placeholder_subtitle_tv.isVisible = value.isNotBlank()
            placeholder_subtitle_tv.text = value
        }

    var primaryButtonText: String = EMPTY_STRING
        set(value) {
            field = value
            placeholder_primary_btn.setTextOrGone(value)
            // Враппер с тенью так же необходимо скрывать так как его высота != 0
            if (!isMiniStyle) updateSecondaryButtonMarginTop(isPrimaryButtonVisible = value.isNotEmpty())
        }

    var secondaryButtonText: String = EMPTY_STRING
        set(value) {
            field = value
            placeholder_secondary_btn.setTextOrGone(value)
        }

    var titleTextAppearance: Int = EMPTY_RES
        set(value) {
            field = value
            placeholder_title_tv.setTextAppearanceStyle(value)
        }
    var subtitleTextAppearance: Int = EMPTY_RES
        set(value) {
            field = value
            placeholder_subtitle_tv.setTextAppearanceStyle(value)
        }

    var onPrimaryButtonClickAction: () -> Unit = {}
    var onSecondaryButtonClickAction: () -> Unit = {}

    private var isMiniStyle = false

    init {
        View.inflate(context, R.layout.view_placeholder, this)

        orientation = VERTICAL
        setBackgroundResource(R.drawable.bg_rect_white)

        obtainAttrs(attrs)
        initViews()
    }

    fun setMiniStyle() {
        isMiniStyle = true
        placeholder_secondary_btn.setTextAppearanceStyle(R.style.Text_Medium_12)
        placeholder_secondary_btn.setTextColor(color(R.color.colorAccent))
        placeholder_subtitle_tv.setTextColor(color(R.color.textColorPrimary))
        placeholder_subtitle_tv.updateLayoutParams<MarginLayoutParams> {
            topMargin = dpToPx(48)
            bottomMargin = 0
        }
        placeholder_secondary_btn.updateLayoutParams<MarginLayoutParams> {
            width = LayoutParams.WRAP_CONTENT
            height = dpToPx(32)
            topMargin = dpToPx(16)
        }
    }

    private fun initViews() {
        placeholder_primary_btn.setOnClickListener { onPrimaryButtonClickAction() }
        placeholder_secondary_btn.setOnClickListener { onSecondaryButtonClickAction() }
    }

    private fun obtainAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlaceholderView)
        title = typedArray.getString(R.styleable.PlaceholderView_title) ?: EMPTY_STRING
        subtitle = typedArray.getString(R.styleable.PlaceholderView_subtitle) ?: EMPTY_STRING
        primaryButtonText = typedArray.getString(R.styleable.PlaceholderView_primaryButtonText)
            ?: EMPTY_STRING
        secondaryButtonText = typedArray.getString(R.styleable.PlaceholderView_secondaryButtonText)
            ?: EMPTY_STRING
        titleTextAppearance = typedArray.getResourceId(R.styleable.PlaceholderView_titleTextAppearance, EMPTY_RES)
        subtitleTextAppearance = typedArray.getResourceId(R.styleable.PlaceholderView_subtitleTextAppearance, EMPTY_RES)
        typedArray.recycle()
    }

    private fun updateSecondaryButtonMarginTop(isPrimaryButtonVisible: Boolean) {
        val margin = if (isPrimaryButtonVisible) dpToPx(0) else dpToPx(24)
        placeholder_secondary_btn.updateLayoutParams<MarginLayoutParams> {
            topMargin = margin
        }
    }
}
