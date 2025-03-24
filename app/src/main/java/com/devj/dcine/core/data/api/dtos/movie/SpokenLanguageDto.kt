package com.devj.dcine.core.data.api.dtos.movie


import com.devj.dcine.features.detail.domain.SpokenLanguage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguageDto(
    @SerialName("english_name")
    val englishName: String,
    @SerialName("iso_639_1")
    val iso6391: String,
    @SerialName("name")
    val name: String
) {
    fun toDomain(): SpokenLanguage =
        SpokenLanguage(
            englishName = englishName,
            iso6391 = iso6391,
            name = name
        )
}