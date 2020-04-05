package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.LookingFor
import ru.sharipov.podcaster.i_network.network.Transformable

data class LookingForResponse(
    @SerializedName("cohosts") val cohosts: Boolean?,
    @SerializedName("cross_promotion") val crossPromotion: Boolean?,
    @SerializedName("sponsors") val sponsors: Boolean?,
    @SerializedName("guests") val guests: Boolean?
) : Transformable<LookingFor> {

    override fun transform(): LookingFor {
        return LookingFor(
            cohosts = cohosts ?: false,
            crossPromotion = crossPromotion ?: false,
            sponsors = sponsors ?: false,
            guests = guests ?: false
        )
    }
}