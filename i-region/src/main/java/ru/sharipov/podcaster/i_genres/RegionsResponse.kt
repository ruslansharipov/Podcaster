package ru.sharipov.podcaster.i_genres

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.Region
import ru.sharipov.podcaster.i_network.network.Transformable

data class RegionsResponse(
    @SerializedName("regions") val regions: Map<String, String>?
) : Transformable<List<Region>> {

    override fun transform(): List<Region> {
        return regions
            ?.map { entry -> Region(entry.key, entry.value) }
            ?: listOf()
    }
}