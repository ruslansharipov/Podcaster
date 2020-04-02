package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.CuratedItem
import ru.sharipov.podcaster.i_network.network.Transformable
import ru.sharipov.podcaster.i_network.network.transformCollection
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList

class CuratedResponse(
    @SerializedName("total") val total: Int?,
    @SerializedName("has_next") val hasNext: Boolean?,
    @SerializedName("page_number") val pageNumber: Int?,
    @SerializedName("has_previous") val hasPrevious: Boolean?,
    @SerializedName("curated_lists") val curatedLists: List<CuratedItemResponse>?,
    @SerializedName("next_page_number") val nextPageNumber: Int?,
    @SerializedName("previous_page_number") val previousPageNumber: Int?
) : Transformable<DataList<CuratedItem>> {

    override fun transform(): DataList<CuratedItem> {
        val totalItemsCount = total ?: 0
        val curatedItems = curatedLists.transformCollection()
        val page = pageNumber ?: 0
        val pageSize = curatedItems.size
        val totalPagesCount = totalItemsCount / if (pageSize > 0) pageSize else 1
        return DataList(curatedItems, page, pageSize, totalItemsCount, totalPagesCount)
    }
}