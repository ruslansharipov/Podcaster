package ru.sharipov.podcaster.f_player.media

import ru.sharipov.podcaster.domain.Episode

class MediaQueue {

    private val queue: MutableList<Episode> = mutableListOf()
    private var index: Int = 0

    val list: List<Episode>
        get() = queue

    val currentIndex: Int
        get() = index

    val current: Episode?
        get() = if (!isEmpty()) queue[index] else null

    val next: Episode?
        get() = if (hasNext()) queue[++index] else current

    val previous: Episode?
        get() = if (hasPrevious()) queue[--index] else current

    fun setQueue(media: List<Episode>, newIndex: Int = 0) {
        queue.clear()
        queue.addAll(media)
        index = newIndex
    }

    fun addQueue(media: Episode) {
        queue.add(media)
    }

    fun removeQueue(media: Episode) {
        if (!hasNext()) index -= 1
        queue.remove(media)
    }

    fun hasNext(): Boolean {
        return !isEmpty() && queue.size > index + 1
    }

    fun hasPrevious(): Boolean {
        return !isEmpty() && index - 1 >= 0
    }

    private fun isEmpty(): Boolean {
        return queue.isEmpty()
    }
}