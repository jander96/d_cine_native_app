package com.devj.dcine.core.data.local.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.devj.dcine.features.detail.domain.BelongsToCollection
import com.devj.dcine.features.detail.domain.Genre
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.detail.domain.ProductionCompany
import com.devj.dcine.features.detail.domain.ProductionCountry
import com.devj.dcine.features.detail.domain.SpokenLanguage
import java.time.LocalDateTime

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    @Embedded val belongsToCollection: BelongsToCollectionEmbeddable?, // objeto anidado
    val budget: Int,
    val homepage: String,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val sawAt: Long? = LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.UTC) // Timestamp in seconds
)

@Entity(
    tableName = "movie_genres",
    primaryKeys = ["movieId", "genreId"]
)
data class MovieGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
)

@Entity(tableName = "production_company")
data class ProductionCompanyEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val logoPath: String?,
    val countryCode: String,
    @ForeignKeyInfo(referenceTo = MovieDetail::class)
    val movieId: Int
)

@Entity(tableName = "production_country")
data class ProductionCountryEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val iso3166_1: String,
    val name: String,
    val movieId: Int
)

@Entity(tableName = "spoken_language")
data class SpokenLanguageEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val iso639_1: String,
    val englishName: String,
    val name: String,
    @ForeignKeyInfo(referenceTo = MovieDetail::class)
    val movieId: Int
)

@Entity(tableName = "origin_country")
data class OriginCountryEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val countryCode: String,
    @ForeignKeyInfo(referenceTo = MovieDetail::class)
    val movieId: Int
)

data class BelongsToCollectionEmbeddable(
    val collectionId: Int,
    val name: String,
    val collectionPosterPath: String?,
    val collectionBackdropPath: String?
)

data class MovieDetailWithRelations(
    @Embedded val movie: MovieDetailEntity,
    @Relation(
        parentColumn = "id", // Primary key of MovieEntity
        entity = GenreEntity::class,
        entityColumn = "id",   // Primary key of GenreEntity
        associateBy = Junction(
            MovieGenreCrossRef::class,
            parentColumn = "movieId", // Column in MovieGenreCrossRefEntity linking to MovieEntity
            entityColumn = "genreId"  // Column in MovieGenreCrossRefEntity linking to GenreEntity
        )
    )
    val genres: List<GenreEntity>,

    @Relation(parentColumn = "id", entityColumn = "movieId")
    val productionCompanies: List<ProductionCompanyEntity>,

    @Relation(parentColumn = "id", entityColumn = "movieId")
    val productionCountries: List<ProductionCountryEntity>,

    @Relation(parentColumn = "id", entityColumn = "movieId")
    val spokenLanguages: List<SpokenLanguageEntity>,

    @Relation(parentColumn = "id", entityColumn = "movieId")
    val originCountries: List<OriginCountryEntity>
) {
    constructor(movie: MovieDetail) : this(
        movie = MovieDetailEntity(
            id = movie.id,
            adult = movie.adult,
            backdropPath = movie.backdropPath,
            belongsToCollection = movie.belongsToCollection?.let {
                BelongsToCollectionEmbeddable(
                    collectionId = it.id,
                    name = it.name,
                    collectionPosterPath = it.poster,
                    collectionBackdropPath = it.backdrop
                )
            },
            budget = movie.budget,
            homepage = movie.homepage,
            imdbId = movie.imdbId,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            popularity = movie.popularity,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            revenue = movie.revenue,
            runtime = movie.runtime,
            status = movie.status,
            tagline = movie.tagline,
            title = movie.title,
            video = movie.video,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount
        ),
        genres = movie.genres.map { GenreEntity(it.id, it.name) },
        productionCompanies = movie.productionCompanies.map {
            ProductionCompanyEntity(
                id = it.id,
                name = it.name,
                logoPath = it.logo,
                countryCode = it.originCountry,
                movieId = movie.id
            )
        },
        productionCountries = movie.productionCountries.map {
            ProductionCountryEntity(
                iso3166_1 = it.iso31661,
                name = it.name,
                movieId = movie.id
            )
        },
        spokenLanguages = movie.spokenLanguages.map {
            SpokenLanguageEntity(
                iso639_1 = it.iso6391,
                englishName = it.englishName,
                name = it.name,
                movieId = movie.id
            )
        },
        originCountries = movie.originCountry.map {
            OriginCountryEntity(
                countryCode = it,
                movieId = movie.id
            )
        }
    )

    fun toDomain(): MovieDetail {
        return MovieDetail(
            id = movie.id,
            adult = movie.adult,
            backdropPath = movie.backdropPath,
            belongsToCollection = movie.belongsToCollection?.let {
                BelongsToCollection(
                    id = it.collectionId,
                    name = it.name,
                    poster = it.collectionPosterPath,
                    backdrop = it.collectionBackdropPath
                )
            },
            budget = movie.budget,
            genres = genres.map { Genre(it.id, it.name) },
            homepage = movie.homepage,
            imdbId = movie.imdbId,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            popularity = movie.popularity,
            posterPath = movie.posterPath,
            productionCompanies = productionCompanies.map {
                ProductionCompany(
                    id = it.id,
                    logo = it.logoPath,
                    name = it.name,
                    originCountry = it.countryCode
                )
            },
            productionCountries = productionCountries.map {
                ProductionCountry(it.iso3166_1, it.name)
            },
            releaseDate = movie.releaseDate,
            revenue = movie.revenue,
            runtime = movie.runtime,
            spokenLanguages = spokenLanguages.map {
                SpokenLanguage(englishName = it.englishName, iso6391 = it.iso639_1, name = it.name)
            },
            status = movie.status,
            tagline = movie.tagline,
            title = movie.title,
            video = movie.video,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            originCountry = originCountries.map { it.countryCode }
        )
    }
}