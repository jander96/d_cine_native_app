package com.devj.dcine.features.detail.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.features.detail.domain.Actor
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.movies.data.MoviesRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieDetailViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val _state: MutableStateFlow<MovieState> = MutableStateFlow(MovieState.Initial)
    val state: StateFlow<MovieState> = _state.asStateFlow()

    suspend fun getMovie(id: Int) {
        try {
            _state.update { MovieState.Loading }
            val movieDeferred: Deferred<MovieDetail> = viewModelScope.async {
                _state.update {
                    MovieState.Loading

                }
                repository.getMovie(id)
            }

            val actorsDeferred: Deferred<List<Actor>> = viewModelScope.async {
                _state.update {
                    MovieState.Loading

                }
                repository.getCast(id)
            }
            _state.update {
                MovieState.Success(
                    movie = movieDeferred.await(),
                    actors = actorsDeferred.await()
                )
            }

        } catch (e: Exception) {
            _state.update { MovieState.Error(e) }
        }

    }
}

sealed class MovieState {
    data object Initial : MovieState()
    data object Loading : MovieState()
    data class Success(val movie: MovieDetail, val actors: List<Actor>) : MovieState()
    data class Error(val error: Throwable) : MovieState()
}