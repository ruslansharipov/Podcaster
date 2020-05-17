package ru.sharipov.podcaster.base_feature.ui.widget.inset

import android.app.Activity
import android.content.Context
import ru.sharipov.podcaster.base_feature.ui.data.AppInsets
import ru.sharipov.podcaster.base_feature.ui.extesions.doOnApplyInsets

typealias OnSystemInsetsChangedListener = (insetSizes: AppInsets) -> Unit

/**
 * Дефолтная реализация интерфейса [InsetContainer]
 */
class DefaultInsetContainer(context: Context) : InsetContainer {
    override var insets: AppInsets = AppInsets()

    private val insetListeners: HashSet<OnSystemInsetsChangedListener> = hashSetOf()
    private val singleListeners: HashSet<OnSystemInsetsChangedListener> = hashSetOf()

    init {
        val activity = context as? Activity
        activity?.window?.decorView?.doOnApplyInsets { insets ->
            this.insets = insets
            insetListeners.forEach { listener -> listener(insets) }
            if (!insets.isEmpty) {
                singleListeners.forEach { listener -> listener.invoke(insets) }
                singleListeners.clear()
            }
        }
    }

    override fun addOnInsetsChangedListener(listener: OnSystemInsetsChangedListener) {
        this.insetListeners.add(listener)
    }

    override fun removeOnInsetsChangedListener(listener: OnSystemInsetsChangedListener) {
        this.insetListeners.remove(listener)
    }

    override fun setOnInsetsChangedSingleListener(listener: OnSystemInsetsChangedListener) {
        if (insets.isEmpty) {
            singleListeners.add(listener)
        } else {
            listener(insets)
        }
    }
}
