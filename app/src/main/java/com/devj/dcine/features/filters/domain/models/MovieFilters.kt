package com.devj.dcine.features.filters.domain.models

import com.devj.dcine.features.detail.domain.Genre
import com.devj.dcine.features.detail.domain.ProductionCompany

data class MovieFilter(
    val genres: List<Genre> = emptyList(),
    val companies: List<ProductionCompany> = emptyList(),
    val language: String? = null,
    val year : Int? = null,
    val voteAverage : Double? = null,
    val country : String? = null,
    val includeAdult: Boolean = false,
    val includeVideo: Boolean = false,
)