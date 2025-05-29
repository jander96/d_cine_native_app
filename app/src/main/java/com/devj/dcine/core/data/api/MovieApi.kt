package com.devj.dcine.core.data.api

import com.devj.dcine.core.data.api.dtos.PaginatedResponse
import com.devj.dcine.core.data.api.dtos.cast.CastResponse
import com.devj.dcine.core.data.api.dtos.movie.MovieDetailDto
import com.devj.dcine.core.data.api.dtos.movie.MovieDto
import com.devj.dcine.core.data.api.dtos.video.VideoResponseDto
import com.devj.dcine.features.filters.domain.models.MovieFilter
import com.devj.dcine.features.filters.domain.models.SortBy

interface MovieApi {
    suspend fun getPopularMovies(
        page: Int? = null,
        startDate: String? = null,
        endDate: String? = null
    ): PaginatedResponse<MovieDto>

    suspend fun getTopRatedMovies(
        page: Int? = null,
        startDate: String? = null,
        endDate: String? = null
    ): PaginatedResponse<MovieDto>

    suspend fun getNowPlayingMovies(
        page: Int? = null,
        startDate: String? = null,
        endDate: String? = null
    ): PaginatedResponse<MovieDto>

    suspend fun getUpcomingMovies(
        page: Int? = null,
        startDate: String? = null,
        endDate: String? = null
    ): PaginatedResponse<MovieDto>

    suspend fun getMovie(id: Int): MovieDetailDto

    suspend fun getCasting(id: Int): CastResponse

    suspend fun getMovieVideos(id: Int): VideoResponseDto

    suspend fun discoverMovies(
        filter: MovieFilter = MovieFilter(),
        page: Int = 1,
        order: SortBy = SortBy.POPULARITY_DESC,
    ): PaginatedResponse<MovieDto>
}