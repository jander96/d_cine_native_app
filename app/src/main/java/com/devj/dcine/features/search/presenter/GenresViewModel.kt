package com.devj.dcine.features.search.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.core.presenter.AsyncState
import com.devj.dcine.features.detail.domain.Genre
import com.devj.dcine.features.movies.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GenresViewModel(private val repo: MoviesRepository) : ViewModel() {
    private val _state = MutableStateFlow(GenresState())
    val state: StateFlow<GenresState>
        get() = _state

    init {
        viewModelScope.launch{
            loadGenres()
        }
    }

    suspend fun loadGenres() {
        _state.value = _state.value.copy(asyncState = AsyncState.LOADING)
        try {
            val genres = repo.getGenres()
            _state.value = GenresState(asyncState = AsyncState.SUCCESS, genres = genres)
        } catch (e: Throwable) {
            _state.value = GenresState(asyncState = AsyncState.ERROR, error = e)
        }
    }
}

data class GenresState(
    val asyncState: AsyncState = AsyncState.INITIAL,
    val genres: List<Genre> = emptyList(),
    val error: Throwable? = null,
)