package com.devj.dcine.features.filters.data.repository

import androidx.compose.runtime.currentRecomposeScope
import com.devj.dcine.features.filters.domain.models.MovieFilter
import com.devj.dcine.features.filters.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FilterRepositoryImp: FilterRepository {

    private val currentFilter = MutableStateFlow(MovieFilter())

    override fun getMovieFilters(): Flow<MovieFilter> {
        return  currentFilter.asStateFlow()
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