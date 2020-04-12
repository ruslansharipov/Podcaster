package ru.sharipov.podcaster.base.datalist_date

import ru.sharipov.podcaster.domain.SortType
import java.io.Serializable
import java.util.*

/**
 * Позволяет мерджить списки для пагинации.
 * Возник из-за того, что сервер возвращает только дату первого, последнего и следующего эпизода
 * для пачки пагинации. Поэтому не получилось выделить ни limit-offset, ни page-count.
 */
data class MergeList<T>(
    private val data: List<T>,
    private val earliestPubDateMs: Long,
    private val latestPubDateMs: Long,
    private val nextEpisodePubDate: Long
) : List<T>, Serializable {

    fun merge(mergeList: MergeList<T>): MergeList<T> {
        val set = TreeSet<T>()
        set.addAll(this)
        set.addAll(mergeList)

        val isThisThePrevious = nextEpisodePubDate != 0L
                && nextEpisodePubDate < mergeList.nextEpisodePubDate

        return MergeList(
            data = set.toList(),
            earliestPubDateMs = earliestPubDateMs,
            latestPubDateMs = latestPubDateMs,
            nextEpisodePubDate = if (isThisThePrevious) mergeList.nextEpisodePubDate else nextEpisodePubDate
        )
    }

    fun <R> transform(transformer: (T) -> R): MergeList<R> {
        return MergeList(
            data = data.map(transformer),
            earliestPubDateMs = earliestPubDateMs,
            latestPubDateMs = latestPubDateMs,
            nextEpisodePubDate = nextEpisodePubDate
        )
    }

    /**
     * Проверка возможности дозагрузки данных
     *
     * @return
     */
    fun canGetMore(sortType: SortType): Boolean {
        val hasMoreEpisodes = nextEpisodePubDate != 0L
        return when (sortType) {
            SortType.RECENT_FIRST -> hasMoreEpisodes && nextEpisodePubDate < earliestPubDateMs
            SortType.OLDEST_FIRST -> hasMoreEpisodes && nextEpisodePubDate > earliestPubDateMs
        }
    }

    override val size: Int = data.size

    override fun contains(element: T): Boolean = data.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = data.containsAll(elements)

    override fun get(index: Int): T = data[index]

    override fun indexOf(element: T): Int = data.indexOf(element)

    override fun isEmpty(): Boolean = data.isEmpty()

    override fun iterator(): Iterator<T> = data.iterator()

    override fun lastIndexOf(element: T): Int = data.lastIndexOf(element)

    override fun listIterator(): ListIterator<T> = data.listIterator()

    override fun listIterator(index: Int): ListIterator<T> = data.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> = data.subList(fromIndex, toIndex)
}