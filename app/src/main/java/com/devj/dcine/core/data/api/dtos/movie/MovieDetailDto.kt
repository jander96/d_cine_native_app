package com.devj.dcine.core.data.api.dtos.movie


import com.devj.dcine.core.utils.helpers.PathImageHelper
import com.devj.dcine.features.detail.domain.MovieDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollectionDto?,
    @SerialName("budget")
    val budget: Int,
    @SerialName("genres")
    val genres: List<GenreDto>,
    @SerialName("homepage")
    val homepage: String,
    @SerialName("id")
    val id: Int,
    @SerialName("imdb_id")
    val imdbId: String,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryDto>,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("revenue")
    val revenue: Int,
    @SerialName("runtime")
    val runtime: Int,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto>,
    @SerialName("status")
    val status: String,
    @SerialName("tagline")
    val tagline: String,
    @SerialName("title")
    val title: String,
    @SerialName("video")
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
) {
    fun toDomain() : MovieDetail {
        return MovieDetail(
             adult = adult,
             backdropPath = PathImageHelper.getImageUrl(backdropPath),
             belongsToCollection = belongsToCollection?.toDomain(),
             budget = budget,
             genres = genres.map { it.toDomain() },
             homepage = homepage,
             id = id,
             imdbId = imdbId,
             originCountry = originCountry,
             originalLanguage = originalLanguage,
             originalTitle = originalTitle,
             overview = overview,
             popularity = popularity,
             posterPath = PathImageHelper.getImageUrl(posterPath),
             productionCompanies = productionCompanies.map { it.toDomain() },
             productionCountries = productionCountries.map { it.toDomain() },
             releaseDate = releaseDate,
             revenue = revenue,
             runtime = runtime,
             spokenLanguages = spokenLanguages.map { it.toDomain() },
             status = status,
             tagline = tagline,
             title = title,
             video = video,
             voteAverage = voteAverage,
             voteCount = voteCount
        )
    }
}