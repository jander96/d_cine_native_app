package com.devj.dcine.core.data.api.dtos.movie


import com.devj.dcine.features.detail.domain.ProductionCountry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryDto(
    @SerialName("iso_3166_1")
    val iso31661: String,
    @SerialName("name")
    val name: String
) {
    fun toDomain(): ProductionCountry =
        ProductionCountry(
            iso31661 = iso31661,
            name = name
        )
}