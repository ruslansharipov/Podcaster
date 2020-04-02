package ru.sharipov.podcaster.base_feature.ui.message_controller

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.children
import com.google.android.material.appbar.AppBarLayout
import ru.sharipov.podcaster.base_feature.R
import ru.surfstudio.android.core.ui.provider.ActivityProvider

/**
 * Класс позволяющий отображать уведомления вверху текущей активити.
 */
class TopSnackController(private val activityProvider: ActivityProvider) : IconMessageController {

    /**
     * Закрывает отображаемое в данный момент уведомление
     */
    override fun closeSnack() {
        Alerter.hide()
    }

    /**
     * Показывает уведомление с заданными параметрами
     *
     * @param stringId - id строкового ресурса текста уведомления
     * @param backgroundColor - id ресурса цвета фона уведомления
     * @param iconResId - id ресурса изображения иконки уведомления
     * @param durationMillis - продолжительность отображения уведомления в миллисекундах
     * @param listener - лямбда, вызываемая по клику на показываемое уведомление
     */
    override fun show(
        @StringRes stringId: Int,
        @ColorRes backgroundColor: Int?,
        @DrawableRes iconResId: Int?,
        durationMillis: Long,
        listener: (view: View) -> Unit
    ) {

        val message = activityProvider.get().resources.getString(stringId)
        show(message, backgroundColor, iconResId, durationMillis, listener)
    }

    /**
     * то же, что @see [show] только принимает не id строкового ресурса, а [String]
     */
    override fun show(
        message: String,
        @ColorRes backgroundColor: Int?,
        @DrawableRes iconResId: Int?,
        durationMillis: Long,
        listener: (view: View) -> Unit
    ) {
        Alerter.hide()

        val activity = activityProvider.get()
        val backgroundColorTintRes = backgroundColor ?: android.R.color.black
        val backgroundColorTint = ContextCompat.getColor(activity as Context, backgroundColorTintRes)
        val backgroundRes = R.drawable.bg_snack_default
        val background = ContextCompat.getDrawable(activity, backgroundRes)
            ?.apply {
                DrawableCompat.setTint(this, backgroundColorTint)
            }

        Alerter.create(activity).apply {
            setText(message)
            setDuration(durationMillis)
            setOnClickListener(View.OnClickListener { view -> listener(view) })
            background?.let(::setBackgroundDrawable)
            iconResId?.let(::setIcon)
            enableSwipeToDismiss()
            setupToolbarMargin()
            show()
        }
    }

    private fun Alerter.setupToolbarMargin() {
        val activity = activityProvider.get()
        val anchor = activity.window.decorView as ViewGroup
        val toolbarTag = anchor.resources.getString(R.string.tag_snack_anchor_view)
        val toolbar = findToolbar(anchor, toolbarTag)
        val appBar = findAppBarLayout(anchor)

        val currentOffset = getAppbarOffset(appBar)
        val isAppBarCollapsed = currentOffset != 0
        val isJustToolbarOnScreen = toolbar != null && appBar == null

        when {
            isJustToolbarOnScreen ->
                // TODO придумать, как показывать снекбар именно под вьюхой.
                // TODO сейчас снеки просто отступают размер главной вьюхи от начала экрана
                addAdditionalMargin(toolbar?.height ?: return)
            isAppBarCollapsed ->
                addAdditionalMargin(anchor.resources.getDimensionPixelSize(R.dimen.toolbar_size))
        }
    }

    private fun findToolbar(anchor: ViewGroup, toolbarTag: String): View? {
        anchor.children.forEach {
            if (it is Toolbar || it.tag == toolbarTag) return it
            if (it is ViewGroup) return findToolbar(it, toolbarTag)
        }
        return null
    }

    private fun findAppBarLayout(anchor: ViewGroup): AppBarLayout? {
        anchor.children.forEach {
            if (it is AppBarLayout) return it
            if (it is ViewGroup) return findAppBarLayout(it)
        }
        return null
    }

    private fun getAppbarOffset(appBarLayout: AppBarLayout?): Int {
        appBarLayout ?: return 0
        return appBarLayout.height - appBarLayout.bottom
    }
}
