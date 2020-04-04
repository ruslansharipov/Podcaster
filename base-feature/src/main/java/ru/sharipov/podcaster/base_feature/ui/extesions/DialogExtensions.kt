package ru.sharipov.podcaster.base_feature.ui.extesions

import android.graphics.Rect
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Создает листенер, который слушает появление и исчезновение клавиатуры
 */
fun DialogFragment.createKeyboardVisibilityToggleListener(decorView: View): ViewTreeObserver.OnGlobalLayoutListener {
    return ViewTreeObserver.OnGlobalLayoutListener {
        val view = view ?: return@OnGlobalLayoutListener
        val windowFrame = Rect()
        decorView.getWindowVisibleDisplayFrame(windowFrame)

        val height = decorView.context.resources.displayMetrics.heightPixels
        val diff = height - windowFrame.bottom

        if (diff != 0) {
            val behaviorView = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            behaviorView ?: return@OnGlobalLayoutListener
            val bottomSheetBehavior = BottomSheetBehavior.from(behaviorView)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            view.post {
                view.setPadding(0, 0, 0, diff)
            }
        } else {
            if (view.paddingBottom != 0) view.setPadding(0, 0, 0, diff)
        }
    }
}

/**
 * Добавляет листенер диалогу, который разворачивает bottomSheetDialog
 * на полную величину как только он будет показан
 */
fun BottomSheetDialogFragment.expandOnShow() {
    dialog?.setOnShowListener { dialog ->
        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheetInternal = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED)
    }
}

fun BottomSheetDialog.expand() {
    behavior.state = BottomSheetBehavior.STATE_EXPANDED
}

fun BottomSheetDialogFragment.expand() {
    (dialog as BottomSheetDialog).expand()
}

/**
 * Установка высоты, на которую [BottomSheetDialog] будет изначально раскрываться.
 *
 * @param peekHeight высота (px).
 */
fun BottomSheetDialogFragment.setPeekHeight(peekHeight: Int) {
    val bottomSheet =
        dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
    val behavior = BottomSheetBehavior.from<FrameLayout>(bottomSheet)
    behavior.peekHeight = peekHeight
}

/**
 * Добавляет листенер диалогу, который разворачивает bottomSheetDialog
 * на полную величину как только он будет показан и запрещает диалогу выходить из
 * развернутого состояния
 */
fun BottomSheetDialogFragment.expandOnStartAndRestrictCollapsing() {
    dialog?.setOnShowListener { dialog ->
        val bottomSheetDialog = dialog as BottomSheetDialog
        bottomSheetDialog.behavior.run {
            expand()
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        expand()
                    }
                }
            })
        }
    }
}

/**
 * Разворачивает диалог на максимальную высоту
 */
fun BottomSheetDialogFragment.expandToMaximumHeight() {
    (dialog as BottomSheetDialog).behavior.peekHeight = displayHeight
}

private fun <V : View> BottomSheetBehavior<V>.expand() {
    state = BottomSheetBehavior.STATE_EXPANDED
}
