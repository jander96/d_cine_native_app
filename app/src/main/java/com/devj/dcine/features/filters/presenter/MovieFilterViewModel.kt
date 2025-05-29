package com.devj.dcine.features.filters.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.features.filters.domain.models.MovieFilter
import com.devj.dcine.features.filters.domain.repository.FilterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieFilterViewModel(
    private val repo: FilterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MovieFilter())
    val state: StateFlow<MovieFilter> = _state

    init {
        observeFilters()
    }

    private fun observeFilters() {
        viewModelScope.launch {
            repo.getMovieFilters()
                .collectLatest { filters ->
                    _state.update { filters }
                }
        }
    }

    fun setMovieFilters(fn: (filters: MovieFilter) -> MovieFilter) {
        viewModelScope.launch {
            repo.setMovieFilters(fn(_state.value))
        }
    }
}