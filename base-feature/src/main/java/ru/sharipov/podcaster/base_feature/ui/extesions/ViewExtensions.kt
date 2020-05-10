package ru.sharipov.podcaster.base_feature.ui.extesions

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.*
import androidx.core.view.ViewCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import ru.sharipov.podcaster.base_feature.ui.data.AppInsets
import ru.surfstudio.android.animations.anim.AnimationUtil
import ru.surfstudio.android.animations.anim.fadeIn
import ru.surfstudio.android.animations.anim.fadeOut
import ru.surfstudio.android.utilktx.ktx.ui.view.actionIfChanged
import kotlin.math.abs
import kotlin.math.roundToLong

/** Возвращает видимый на экране [Rect]. */
val View.globalRect: Rect get() = Rect().also { getGlobalVisibleRect(it) }

/** Возвращает расположение верхней части [View] на экране по оси Y. */
val View.globalTop: Int get() = globalRect.top

/** Возвращает расположение нижней части [View] на экране по оси Y. */
val View.globalBottom: Int get() = globalRect.bottom

/** Возвращает расположение левой части [View] на экране по оси X. */
val View.globalLeft: Int get() = globalRect.left

/** Возвращает расположение правой части [View] на экране по оси X. */
val View.globalRight: Int get() = globalRect.right

/** Возвращает расположение верхней части [View] на экране по оси Y, трансформированное во [Float]. */
val View.globalTopF: Float get() = globalTop.toFloat()

/** Возвращает расположение нижней части [View] на экране по оси Y, трансформированное во [Float]. */
val View.globalBottomF: Float get() = globalBottom.toFloat()

/** Возвращает расположение левой части [View] на экране по оси X, трансформированное во [Float]. */
val View.globalLeftF: Float get() = globalLeft.toFloat()

/** Возвращает расположение правой части [View] на экране по оси X, трансформированное во [Float]. */
val View.globalRightF: Float get() = globalRight.toFloat()

/** Возвращает значение [View.getLeft], трансформированное во [Float]. */
val View.leftF: Float get() = left.toFloat()

/** Возвращает значение [View.getTop], трансформированное во [Float]. */
val View.topF: Float get() = top.toFloat()

/** Возвращает значение [View.getRight], трансформированное во [Float]. */
val View.rightF: Float get() = right.toFloat()

/** Возвращает значение [View.getBottom], трансформированное во [Float]. */
val View.bottomF: Float get() = bottom.toFloat()

/** Возвращает значение [View.getHeight], трансформированное во [Float]. */
val View.heightF: Float get() = height.toFloat()

/** Возвращает значение [View.getWidth], трансформированное во [Float]. */
val View.widthF: Float get() = width.toFloat()

/**
 * Конвертация `density point'ов` в `пиксели`.
 * */
fun View.dpToPx(value: Int): Int {
    return context.dpToPx(value)
}

/**
 * Конвертация `density point'ов` в `пиксели`.
 * */
fun View.dpToPx(value: Long): Int {
    return context.dpToPx(value)
}

/**
 * Конвертация `density point'ов` в `пиксели`.
 * */
fun View.dpToPx(value: Float): Int {
    return context.dpToPx(value)
}

/**
 * Извлечение строки из ресурсов.
 * */
fun View.string(@StringRes id: Int, vararg formatArgs: Any): String {
    return context.getString(id, *formatArgs)
}

/**
 * Извлечение количественной строки с одним аргументом из ресурсов.
 * */
fun View.quantityString(@PluralsRes id: Int, quantity: Int): String {
    return context.quantityString(id, quantity)
}

/**
 * Извлечение количественной строки из ресурсов.
 * */
fun View.quantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
    return context.quantityString(id, quantity, *formatArgs)
}

/**
 * Извлечение цвета из ресурсов.
 * */
fun View.color(@ColorRes id: Int): Int {
    return context.color(id)
}

/**
 * Извлечение `dimen` из ресурсов.
 * */
fun View.dimen(@DimenRes id: Int): Int {
    return context.dimen(id)
}

/**
 * Извлечение Float значений из dimen ресурсов.
 */
fun View.dimenF(@DimenRes id: Int): Float {
    return context.dimenF(id)
}

/**
 * Извлечение `drawable` из ресурсов.
 * */
fun View.drawable(@DrawableRes id: Int): Drawable? {
    return context.drawable(id)
}

/**
 * Извлечение строкового массива из ресурсов.
 * */
fun View.stringArr(@ArrayRes id: Int): Array<String> {
    return context.stringArr(id)
}

/**
 * Извлечение целочисленного массива из ресурсов.
 * */
fun View.intArr(@ArrayRes id: Int): IntArray {
    return context.intArr(id)
}

/**
 * Extension-метод для отображения установленного в этой `View` `ripple`-эффекта.
 * */
fun View.showRipple() {
    isPressed = true
    isPressed = false
}

/**
 * Плавно скрывающая или показывающая View анимация.
 *
 * @param condition условие, в зависимости от которого view будет показана или скрыта
 */
fun View.fadeOutIf(
    condition: Boolean,
    duration: Long = AnimationUtil.ANIM_LEAVING,
    defaultAlpha: Float = 1.0f,
    endAction: (() -> Unit)? = null
) {
    if (condition) {
        if (visibility != View.GONE) {
            fadeOut(defaultAlpha = defaultAlpha, duration = duration, endAction = endAction)
        }
    } else {
        if (visibility != View.VISIBLE) {
            fadeIn(defaultAlpha = defaultAlpha, duration = duration, endAction = endAction)
        }
    }
}

/**
 * Установка видимости с плавным показом или скрытием.
 */
fun View.setVisibilityFade(isVisible: Boolean) = fadeOutIf(!isVisible, 150L)

/**
 * Задать действие по событию onGlobalLayout, снимающееся после первого срабатывания
 *
 * @param onGlobalLayoutAction действие, происходящее при событии onGlobalLayout
 */
fun View.setOnGlobalLayoutListenerSingle(onGlobalLayoutAction: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            onGlobalLayoutAction()
        }
    })
}

/**
 * Выполнить действие [action], если [data] была изменена с момента последнего вызова этого метода.
 *
 * Если `data == null` или `data == previous_data` -> [action] не будет вызван.
 * */
fun <T : Any, V : View> V.performIfChanged(data: T?, action: V.(T) -> Unit) {
    actionIfChanged(data, { if (data != null) action(data) })
}

fun <T1 : Any, T2 : Any, V : View> V.performIfChanged(data1: T1?, data2: T2, action: V.(T1, T2) -> Unit) {
    actionIfChanged(data1, data2, { d1, d2 -> if (d1 != null && d2 != null) action(d1, d2) })
}

/**
 * Анимирует `View.translationY` до состояния, когда `View.translationX == 0f`.
 * */
fun View.slideIn(duration: Long, interpolator: Interpolator = LinearInterpolator()) {
    if (!isVisible) translationY = heightF
    verticalSlideTo(
        targetTranslationY = 0f,
        duration = calculateVerticalSlideAnimDuration(0f, duration),
        interpolator = interpolator
    )
    isVisible = true
}

/**
 * Анимирует `View.translationY` до состояния, когда `View.translationX == heightF`.
 * */
fun View.slideOut(duration: Long, interpolator: Interpolator = LinearInterpolator()) {
    if (!isVisible) translationY = 0f
    verticalSlideTo(
        targetTranslationY = heightF,
        duration = calculateVerticalSlideAnimDuration(heightF, duration),
        interpolator = interpolator,
        endAction = { isInvisible = true }
    )
    isVisible = true
}

/**
 * Анимирует `View.translationY`.
 *
 * @param targetTranslationY конечная позиция, в которую будет перемещена `View`.
 * @param duration длительность анимации перемещения `View`.
 * @param interpolator интерполятор анимации перемещения `View`.
 * @param endAction действие, выполняемое по окончании анимации `View`.
 * */
fun View.verticalSlideTo(
    targetTranslationY: Float,
    duration: Long,
    interpolator: Interpolator,
    endAction: (() -> Unit)? = null
) {
    clearAnimation()
    animate()
        .translationY(targetTranslationY)
        .setDuration(duration)
        .setInterpolator(interpolator)
        .withEndAction { endAction?.invoke() }
        .start()
}

/**
 * Анимирует `View.translationX`.
 *
 * @param targetTranslationX конечная позиция, в которую будет перемещена `View`.
 * @param duration длительность анимации перемещения `View`.
 * @param interpolator интерполятор анимации перемещения `View`.
 * @param endAction действие, выполняемое по окончании анимации `View`.
 * */
fun View.horizontalSlideTo(
    targetTranslationX: Float,
    duration: Long,
    interpolator: Interpolator,
    endAction: (() -> Unit)? = null
) {
    clearAnimation()
    animate()
        .translationX(targetTranslationX)
        .setDuration(duration)
        .setInterpolator(interpolator)
        .withEndAction { endAction?.invoke() }
        .start()
}

/**
 * Рассчитать длительность вертикальной `slide-анимации` на основе входных параметров.
 *
 * @param targetTranslationY конечная позиция, в которую будет перемещена `View`.
 * @param animDuration длительность анимации перемещения `View`.
 * */
fun View.calculateVerticalSlideAnimDuration(
    targetTranslationY: Float,
    animDuration: Long
): Long {
    val translationDiff = abs(targetTranslationY - translationY)
    return when {
        translationDiff == 0f -> 0L
        translationDiff == targetTranslationY || height == 0 -> animDuration
        else -> (animDuration / heightF * translationDiff).roundToLong()
    }
}

/**
 * Рассчитать длительность горизонтальной `slide-анимации` на основе входных параметров.
 *
 * @param targetTranslationX конечная позиция, в которую будет перемещена `View`.
 * @param animDuration длительность анимации перемещения `View`.
 * */
fun View.calculateHorizontalSlideAnimDuration(
    targetTranslationX: Float,
    animDuration: Long
): Long {
    val translationDiff = abs(targetTranslationX - translationX)
    return when {
        translationDiff == 0f -> 0L
        translationDiff == targetTranslationX || width == 0 -> animDuration
        else -> (animDuration / widthF * translationDiff).roundToLong()
    }
}

private fun View.isKeyboardAppeared(bottomInset: Int): Boolean {
    return bottomInset / resources.displayMetrics.heightPixels.toDouble() > .15
}

fun View.doOnApplyInsets(listener: (AppInsets) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        val topInset = insets.systemWindowInsetTop
        val bottomInset = insets.systemWindowInsetBottom

        val hasKeyboard = isKeyboardAppeared(bottomInset)
        val keyboardHeight = if (hasKeyboard) bottomInset else 0
        val navigationBarBottomInset = if (hasKeyboard) 0 else bottomInset
        listener(AppInsets(topInset, navigationBarBottomInset, keyboardHeight))
        insets
    }
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }
            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

fun View.observeSystemBarsSize(): Observable<AppInsets> {
    return Observable.create<AppInsets> { emitter: ObservableEmitter<AppInsets> ->
        doOnApplyInsets(emitter::onNext)
    }
}
