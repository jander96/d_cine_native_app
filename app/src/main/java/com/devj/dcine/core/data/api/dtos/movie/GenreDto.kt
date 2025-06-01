package com.devj.dcine.core.data.api.dtos.movie


import com.devj.dcine.features.detail.domain.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
) {
    fun toDomain(): Genre =
        Genre(id = id, name = name)
}

@Serializable
data class  GenreListDto(
    val genres: List<GenreDto>
) {
    fun toDomain(): List<Genre> = genres.map { it.toDomain() }
}