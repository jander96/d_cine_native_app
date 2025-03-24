package com.devj.dcine.core.data.api.dtos.movie


import com.devj.dcine.core.utils.helpers.PathImageHelper
import com.devj.dcine.features.detail.domain.BelongsToCollection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BelongsToCollectionDto(
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("poster_path")
    val posterPath: String?
) {
    fun toDomain() = BelongsToCollection(
        id = id,
        backdrop = backdropPath?.let { PathImageHelper.getImageUrl(it) } ,
        name = name,
        poster = posterPath?.let { PathImageHelper.getImageUrl(it) }
    )
}