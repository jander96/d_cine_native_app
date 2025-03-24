package com.devj.dcine.core.data.api.dtos.movie


import com.devj.dcine.core.utils.helpers.PathImageHelper
import com.devj.dcine.features.detail.domain.ProductionCompany
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyDto(
    @SerialName("id")
    val id: Int,
    @SerialName("logo_path")
    val logoPath: String?,
    @SerialName("name")
    val name: String,
    @SerialName("origin_country")
    val originCountry: String
) {
    fun toDomain(): ProductionCompany =
        ProductionCompany(
            id = id,
            logo = logoPath?.let { PathImageHelper.getImageUrl(it) } ,
            name = name,
            originCountry = originCountry
        )

}