package com.devj.dcine.features.home.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.movies.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(private val repo: MoviesRepository) : ViewModel() {
    private val _state: MutableStateFlow<List<MovieDetail>> = MutableStateFlow(emptyList())
    val state: StateFlow<List<MovieDetail>> = _state

    init {
        viewModelScope.launch {
            repo.getLastSeenMovies().collectLatest { movies ->
                _state.update {
                    movies
                }
            }
        }
    }
}