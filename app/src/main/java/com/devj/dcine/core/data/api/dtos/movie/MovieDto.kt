package com.devj.dcine.core.data.api.dtos.movie

import com.devj.dcine.core.utils.helpers.PathImageHelper
import com.devj.dcine.features.movies.domain.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class MovieDto(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
) {
    fun toDomain(): Movie =
        Movie(
            id = id,
            adult = adult,
            backdropPath = backdropPath?.let { PathImageHelper.getImageUrl(it) }  ?: "https://blog.springshare.com/wp-content/uploads/2010/02/nc-md.gif" ,
            genreIds = genreIds,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath?.let { PathImageHelper.getImageUrl(it) }  ?: "https://blog.springshare.com/wp-content/uploads/2010/02/nc-md.gif" ,
            releaseDate = if(releaseDate.isNotEmpty()) LocalDate.parse(releaseDate) else null,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount
        )

}