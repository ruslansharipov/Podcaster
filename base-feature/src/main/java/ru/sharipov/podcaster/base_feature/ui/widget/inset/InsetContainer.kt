package ru.sharipov.podcaster.base_feature.ui.widget.inset

import ru.sharipov.podcaster.base_feature.ui.data.AppInsets

/**
 * Контейнер, поглощающий insets и поставляющий информацию о них.
 */
interface InsetContainer {

    val insets: AppInsets

    /**
     * Добавление лиснера на прослушивание изменений [insets]
     */
    fun addOnInsetsChangedListener(listener: OnSystemInsetsChangedListener)

    /**
     * Удаление лиснера прослушивания изменений [insets]
     */
    fun removeOnInsetsChangedListener(listener: OnSystemInsetsChangedListener)

    /**
     * Установка лиснера, срабатывающего моментально, если инсеты проинициализированы,
     * либо ждущего инициализации, и срабатывающего сразу после этого.
     */
    fun setOnInsetsChangedSingleListener(listener: OnSystemInsetsChangedListener)
}
