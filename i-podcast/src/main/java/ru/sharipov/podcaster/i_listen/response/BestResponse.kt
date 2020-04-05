package ru.sharipov.podcaster.i_listen.response


import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.Podcast
import ru.sharipov.podcaster.i_network.network.Transformable
import ru.sharipov.podcaster.i_network.network.transformCollection
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList

data class BestResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("has_previous") val hasPrevious: Boolean?,
    @SerializedName("name") val name: String?,
    @SerializedName("listennotes_url") val listennotesUrl: String?,
    @SerializedName("previous_page_number") val previousPageNumber: Int?,
    @SerializedName("page_number") val pageNumber: Int?,
    @SerializedName("has_next") val hasNext: Boolean?,
    @SerializedName("next_page_number") val nextPageNumber: Int?,
    @SerializedName("parent_id") val parentId: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("podcasts") val podcasts: List<PodcastResponse>?
) : Transformable<DataList<Podcast>> {

    override fun transform(): DataList<Podcast> {
        val totalItemsCount = total ?: 0
        val items = podcasts.transformCollection()
        val page = pageNumber ?: 0
        val pageSize = items.size
        val totalPagesCount = totalItemsCount / if (pageSize > 0) pageSize else 1
        return DataList(items, page, pageSize, totalItemsCount, totalPagesCount)
    }

}