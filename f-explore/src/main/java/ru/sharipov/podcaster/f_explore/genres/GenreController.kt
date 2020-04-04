package ru.sharipov.podcaster.f_explore.genres

import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_genre.view.*
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.f_explore.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

/**
 * Контроллер жанров
 *
 * @param clickListener слушатель кликов по подсекции
 */
class GenreController(
    private val clickListener: (Genre) -> Unit
) : BindableItemController<Genre, GenreController.Holder>() {

    override fun getItemId(data: Genre): String = data.id.toString()

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(
        parent: ViewGroup
    ) : BindableViewHolder<Genre>(parent, R.layout.list_item_genre) {

        private val genreTv = itemView.genre_tv

        private var payload: Genre? = null

        init {
            itemView.setOnClickListener { payload?.let(clickListener) }
        }

        override fun bind(data: Genre) {
            payload = data
            genreTv.text = data.name
        }
    }
}
