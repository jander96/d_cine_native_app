package com.devj.dcine.features.actors.presenter


import androidx.lifecycle.ViewModel
import com.devj.dcine.features.detail.domain.Actor
import com.devj.dcine.features.movies.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ActorsViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val _state :  MutableStateFlow<ActorsViewState> = MutableStateFlow(ActorsViewState.Initial)
    val state : StateFlow<ActorsViewState> = _state.asStateFlow()

    suspend fun getMovie(id: Int) {
        try {
            _state.update { ActorsViewState.Loading }
            val response = repository.getCast(id)
            _state.update { ActorsViewState.Success(response) }
        } catch (e: Exception) {
            _state.update { ActorsViewState.Error(e) }
        }
    }

}

sealed class ActorsViewState {
    data object  Initial : ActorsViewState()
    data object  Loading: ActorsViewState()
    data class Success(val actors: List<Actor>) : ActorsViewState()
    data class Error(val error: Throwable) : ActorsViewState()
}