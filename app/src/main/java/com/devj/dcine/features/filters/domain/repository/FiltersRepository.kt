package com.devj.dcine.features.filters.domain.repository

import com.devj.dcine.features.filters.domain.models.MovieFilter
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getMovieFilters(): Flow<MovieFilter>
    fun setMovieFilters(filters: MovieFilter)
    suspend fun getMovieFiltersFromStorage(): MovieFilter
    fun clearMovieFilters()
}