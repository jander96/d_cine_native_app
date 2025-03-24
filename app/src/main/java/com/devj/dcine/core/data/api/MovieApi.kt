package com.devj.dcine.core.data.api

import com.devj.dcine.core.data.api.dtos.PaginatedResponse
import com.devj.dcine.core.data.api.dtos.movie.MovieDetailDto
import com.devj.dcine.core.data.api.dtos.movie.MovieDto

interface MovieApi {
    suspend fun getPopularMovies(
        page: Int? = null,
        startDate: String? = null,
        endDate: String? = null
    ): PaginatedResponse<MovieDto>

    suspend fun getMovie(id: Int): MovieDetailDto
}