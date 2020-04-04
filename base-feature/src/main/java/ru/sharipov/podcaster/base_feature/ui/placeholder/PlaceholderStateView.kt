package ru.sharipov.podcaster.base_feature.ui.placeholder

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.view_placeholder_state_view.view.*
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.extesions.color
import ru.sharipov.podcaster.base_feature.ui.extesions.fadeOutIf
import ru.sharipov.podcaster.base_feature.ui.placeholder.PlaceholderState.*
import ru.sharipov.podcaster.i_network.network.toObservable
import ru.surfstudio.android.core.mvi.impls.ui.dialog.standard.EMPTY_RES
import ru.surfstudio.android.rx.extension.scheduler.MainThreadImmediateScheduler
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.Error

/**
 * Вью, плавно переключающая видимость элементов в зависимости от состояния
 */
open class PlaceholderStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val STATE_TOGGLE_DELAY_MS = 250L
    }

    /**
     * Слушатель кликов emptyState
     */
    var emptyClickListener: () -> Unit = {}

    /**
     * Слушатель кликов errorState
     */
    var errorClickListener: () -> Unit = {}

    /**
     * Слушатель кликов notFoundState
     */
    var notFoundClickListener: () -> Unit = {}

    private var loadStateRelay: BehaviorRelay<PlaceholderState> = BehaviorRelay.create()
    private var stateDisposable = Disposables.disposed()
    private val isFirstEmission = AtomicBoolean(true)
    private var isFirstStateApplying: Boolean = true
    private var isMiniStyle: Boolean = false

    var errorBtnTitleRes: Int = 0
    var errorIconRes: Int = 0
    var errorTitleRes: Int = 0
    var errorSubtitleRes: Int = 0
    var errorTitleText: CharSequence = EMPTY_STRING
    var errorSubtitleText: CharSequence = EMPTY_STRING

    var emptyBtnTitleRes: Int = 0
    var emptyIconRes: Int = 0
    var emptyStateTitleRes: Int = 0
    var emptyStateSubTitleRes: Int = 0
    var emptyStateTitleText: CharSequence = EMPTY_STRING
    var emptyStateSubtitleText: CharSequence = EMPTY_STRING

    var notFoundBtnTitleRes: Int = 0
    var notFoundIconRes: Int = 0
    var notFoundTitleRes: Int = 0
    var notFoundSubtitleRes: Int = 0
    var notFoundTitleText: CharSequence = EMPTY_STRING
    var notFoundSubtitleText: CharSequence = EMPTY_STRING

    var transparentBgColor: Int = 0
    var isEmptyStateTransparent: Boolean = false
    var isTransparentLoadingVisible: Boolean = false
    var areTransitionsAnimated: Boolean = true

    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_placeholder_state_view, this@PlaceholderStateView)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PlaceholderStateView)

        initAnimations(ta)
        initShimmer(ta)
        initErrorState(ta)
        initEmptyState(ta)
        initNotFoundState(ta)
        initTransparentState(ta)

        isMiniStyle = ta.getBoolean(R.styleable.PlaceholderStateView_isMiniStyle, false)
        if (isMiniStyle) {
            // в режиме isMiniStyle=true устанавливаем не требующиеся к отображению ресурсы в EMPTY_RES
            emptyIconRes = EMPTY_RES
            errorIconRes = EMPTY_RES
            notFoundIconRes = EMPTY_RES
            errorTitleRes = R.string.empty_string
            view_placeholder.setMiniStyle()
        }

        ta.recycle()
    }

    private fun initNotFoundState(ta: TypedArray) {
        notFoundBtnTitleRes =
            ta.getResourceId(R.styleable.PlaceholderStateView_notFoundBtnTitle, R.string.empty_string)
        notFoundIconRes =
            ta.getResourceId(R.styleable.PlaceholderStateView_notFoundIcon, R.drawable.empty_drawable)
        notFoundTitleRes = ta.getResourceId(R.styleable.PlaceholderStateView_notFoundTitle, R.string.empty_string)
        notFoundSubtitleRes = ta.getResourceId(R.styleable.PlaceholderStateView_notFoundSubTitle, R.string.empty_string)
    }

    private fun initAnimations(ta: TypedArray) {
        areTransitionsAnimated =
            ta.getBoolean(R.styleable.PlaceholderStateView_transitionsAnimated, true)
    }

    private fun initTransparentState(ta: TypedArray) {
        isTransparentLoadingVisible =
            ta.getBoolean(R.styleable.PlaceholderStateView_transparentLoadingVisible, false)
        transparentBgColor =
            ta.getColor(
                R.styleable.PlaceholderStateView_transparentBackgroundColor,
                ContextCompat.getColor(context, R.color.light_transparent_background)
            )
        view_transparent_loading_container.setBackgroundColor(transparentBgColor)
    }

    private fun initShimmer(ta: TypedArray) {
        val shimmerId = ta.getResourceId(R.styleable.PlaceholderStateView_shimmerLayout, View.NO_ID)
        if (shimmerId != View.NO_ID) {
            View.inflate(context, shimmerId, view_placeholder_shimmer_container)
        }
    }

    private fun initErrorState(ta: TypedArray) {
        errorTitleRes = ta.getResourceId(R.styleable.PlaceholderStateView_errorTitle, R.string.error_loading_title)
        errorSubtitleRes = ta.getResourceId(R.styleable.PlaceholderStateView_errorSubtitle, R.string.error_loading_message)
        errorIconRes = ta.getResourceId(R.styleable.PlaceholderStateView_errorIcon, R.drawable.empty_drawable) // TODO
        errorBtnTitleRes =
            ta.getResourceId(R.styleable.PlaceholderStateView_errorBtnTitle, R.string.retry_btn)
    }

    private fun initEmptyState(ta: TypedArray) {
        emptyBtnTitleRes =
            ta.getResourceId(R.styleable.PlaceholderStateView_emptyBtnTitle, R.string.empty_string)
        emptyIconRes =
            ta.getResourceId(R.styleable.PlaceholderStateView_emptyIcon, R.drawable.empty_drawable) // TODO
        emptyStateTitleRes = ta.getResourceId(R.styleable.PlaceholderStateView_emptyTitle, R.string.empty_string)
        emptyStateSubTitleRes = ta.getResourceId(R.styleable.PlaceholderStateView_emptySubTitle, R.string.empty_string)
        isEmptyStateTransparent = ta.getBoolean(R.styleable.PlaceholderStateView_emptyStateTransparent, false)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isFirstStateApplying = true
        stateDisposable = loadStateRelay
            .debounce { state ->
                if (isFirstEmission.getAndSet(false)) {
                    state.toObservable()
                } else {
                    state.toObservable().delay(STATE_TOGGLE_DELAY_MS, TimeUnit.MILLISECONDS)
                }
            }
            .observeOn(MainThreadImmediateScheduler)
            .subscribe(::applyNewState)
    }

    private fun applyNewState(newState: PlaceholderState) {
        val isAllGone = newState is None
        val isShimmerVisible = newState is MainLoading
        val isError = newState is Error || newState is NoInternet || newState is NotFound
        val isEmpty = newState is Empty
        val isTransparentLoading = newState is TransparentLoading

        view_placeholder_main_loading_container.changeVisibility(!isAllGone && isShimmerVisible)
        view_placeholder_scrollable_container.changeVisibility(!isAllGone && (isError || isEmpty))
        if (isTransparentLoadingVisible) {
            view_transparent_loading_container.changeVisibility(isTransparentLoading)
        }
        if (isEmpty || isError) updatePlaceholder(newState)
        isFirstStateApplying = false
    }

    private fun updatePlaceholder(newState: PlaceholderState) {
        val data = when (newState) {
            is Empty -> createEmptyPlaceholderData()
            is NotFound -> createNotFoundPlaceholderData()
            else -> createErrorPlaceholderData()
        }
        val backgroundColorRes = if (data.isStateTransparent) R.color.transparent else R.color.white
        val backgroundColor = color(backgroundColorRes)
        view_placeholder.run {
            title = when (data.titleText != EMPTY_STRING) {
                true -> data.titleText
                false -> resources.getString(data.titleRes)
            }
            subtitle = when (data.subtitleText != EMPTY_STRING) {
                true -> data.subtitleText
                false -> resources.getString(data.subtitleRes)
            }
            iconRes = data.stateIconRes
            primaryButtonText = resources.getString(data.primaryBtnTitleRes)
            secondaryButtonText = resources.getString(data.secondaryBtnTitleRes)
            onPrimaryButtonClickAction = data.primaryClickListener
            onSecondaryButtonClickAction = data.secondaryClickListener
            isFocusable = !data.isStateTransparent
            isFocusableInTouchMode = !data.isStateTransparent
            isClickable = !data.isStateTransparent
            setBackgroundColor(backgroundColor)
        }
        view_placeholder_scrollable_container.run {
            setBackgroundColor(backgroundColor)
        }
    }

    private fun createEmptyPlaceholderData(): PlaceholderData {
        return PlaceholderData(
            titleRes = emptyStateTitleRes,
            titleText = emptyStateTitleText,
            subtitleRes = emptyStateSubTitleRes,
            subtitleText = emptyStateSubtitleText,
            stateIconRes = emptyIconRes,
            primaryBtnTitleRes = emptyBtnTitleRes,
            primaryClickListener = emptyClickListener,
            isStateTransparent = isEmptyStateTransparent
        )
    }

    private fun createNotFoundPlaceholderData(): PlaceholderData {
        return PlaceholderData(
            titleRes = notFoundTitleRes,
            titleText = notFoundTitleText,
            subtitleRes = notFoundSubtitleRes,
            subtitleText = notFoundSubtitleText,
            stateIconRes = notFoundIconRes,
            secondaryBtnTitleRes = notFoundBtnTitleRes,
            secondaryClickListener = notFoundClickListener,
            isStateTransparent = isEmptyStateTransparent
        )
    }

    private fun createErrorPlaceholderData(): PlaceholderData {
        return PlaceholderData(
            titleRes = errorTitleRes,
            titleText = errorTitleText,
            subtitleRes = errorSubtitleRes,
            subtitleText = errorSubtitleText,
            stateIconRes = errorIconRes,
            secondaryBtnTitleRes = errorBtnTitleRes,
            secondaryClickListener = errorClickListener
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        if (!stateDisposable.isDisposed) {
            stateDisposable.dispose()
        }
    }

    /**
     * Переключает состояние вью на передаваемое.
     *
     * Если `newState == null` - состояние не изменится.
     */
    fun setState(newState: PlaceholderState?) {
        loadStateRelay.accept(newState ?: return)
    }

    /**
     * Изменение видимости View.
     *
     * В зависимости от параметра [areTransitionsAnimated] либо меняет видимость View с анимацией, либо моментально
     */
    private fun View.changeVisibility(isVisible: Boolean) {
        when {
            isFirstStateApplying -> this.isVisible = isVisible
            areTransitionsAnimated -> fadeOutIf(!isVisible)
            else -> this.isVisible = isVisible
        }
    }

    /**
     * Вспомогательный класс для приведения плейсхолдера в нужное состояние
     */
    private data class PlaceholderData(
        @StringRes val titleRes: Int = R.string.empty_string,
        @StringRes val subtitleRes: Int = R.string.empty_string,
        @StringRes val primaryBtnTitleRes: Int = R.string.empty_string,
        @StringRes val secondaryBtnTitleRes: Int = R.string.empty_string,
        @DrawableRes val stateIconRes: Int = R.drawable.empty_drawable,
        val titleText: CharSequence = EMPTY_STRING,
        val subtitleText: CharSequence = EMPTY_STRING,
        val isStateTransparent: Boolean = false,
        val primaryClickListener: () -> Unit = {},
        val secondaryClickListener: () -> Unit = {}
    )
}
