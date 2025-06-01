package com.devj.dcine.features.filters.domain.repository

import com.devj.dcine.features.filters.domain.models.MovieFilter
import com.devj.dcine.features.filters.domain.models.SortBy
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    fun getMovieFilters(): Flow<MovieFilter>
    fun getMovieOrder(): Flow<SortBy>
    fun setOrder(order: SortBy)
    fun setMovieFilters(filters: MovieFilter)
    suspend fun getMovieFiltersFromStorage(): MovieFilter
    fun clearMovieFilters()
}