package com.devj.dcine.features.filters.data.repository

import androidx.compose.runtime.currentRecomposeScope
import com.devj.dcine.features.filters.domain.models.MovieFilter
import com.devj.dcine.features.filters.domain.models.SortBy
import com.devj.dcine.features.filters.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FilterRepositoryImp: FilterRepository {

    private val currentFilter = MutableStateFlow(MovieFilter())
    private val currentOrder = MutableStateFlow(SortBy.POPULARITY_DESC)

    override fun getMovieFilters(): Flow<MovieFilter> {
        return  currentFilter.asStateFlow()
    }

    override fun getMovieOrder(): Flow<SortBy> {
        return currentOrder.asStateFlow()
    }

    override fun setOrder(order: SortBy) {
       currentOrder.update { order }
    }

    override fun setMovieFilters(filters: MovieFilter) {
        currentFilter.update { filters }
    }

    override suspend fun getMovieFiltersFromStorage(): MovieFilter {
        // TODO: Implement logic to retrieve filters from persistent storage
       return  currentFilter.value
    }

    override fun clearMovieFilters() {
        currentFilter.update { MovieFilter() }
    }
}