package com.devj.dcine.features.detail

import com.devj.dcine.features.detail.domain.BelongsToCollection
import com.devj.dcine.features.detail.domain.Genre
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.detail.domain.ProductionCompany
import com.devj.dcine.features.detail.domain.ProductionCountry
import com.devj.dcine.features.detail.domain.SpokenLanguage

val mockMovieDetail = MovieDetail(
    adult = false,
    backdropPath = "https://m.media-amazon.com/images/I/71Jxq2p5YWL._AC_UF894,1000_QL80_.jpg",
    belongsToCollection = BelongsToCollection(
        id = 1,
        name = "Sample Collection",
        poster = "https://m.media-amazon.com/images/I/71Jxq2p5YWL._AC_UF894,1000_QL80_.jpg",
        backdrop = "https://m.media-amazon.com/images/I/71Jxq2p5YWL._AC_UF894,1000_QL80_.jpg"
    ),
    budget = 100_000_000,
    genres = listOf(
        Genre(id = 1, name = "Action"),
        Genre(id = 2, name = "Adventure")
    ),
    homepage = "https://www.example.com",
    id = 12345,
    imdbId = "tt1234567",
    originCountry = listOf("US"),
    originalLanguage = "en",
    originalTitle = "Original Title",
    overview = "This is a sample overview of the movie.",
    popularity = 8.5,
    posterPath = "https://m.media-amazon.com/images/I/71Jxq2p5YWL._AC_UF894,1000_QL80_.jpg",
    productionCompanies = listOf(
        ProductionCompany(
            id = 1,
            logo = "https://m.media-amazon.com/images/I/71Jxq2p5YWL._AC_UF894,1000_QL80_.jpg",
            name = "Sample Studio",
            originCountry = "US"
        )
    ),
    productionCountries = listOf(
        ProductionCountry(
            iso31661 = "US",
            name = "United States"
        )
    ),
    releaseDate = "2025-12-25",
    revenue = 500_000_000,
    runtime = 120,
    spokenLanguages = listOf(
        SpokenLanguage(
            englishName = "English Name",
            iso6391 = "en",
            name = "English"
        )
    ),
    status = "Released",
    tagline = "This is the tagline.",
    title = "Sample Movie",
    video = false,
    voteAverage = 7.8,
    voteCount = 1000
)