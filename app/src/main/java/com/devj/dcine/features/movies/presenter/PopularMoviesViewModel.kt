package com.devj.dcine.features.movies.presenter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.devj.dcine.core.composables.PaginationState
import com.devj.dcine.features.movies.data.MoviesRepository
import com.devj.dcine.features.movies.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PopularMoviesViewModel(private val repo: MoviesRepository) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
    }

    private val _state = MutableStateFlow(PopularMoviesState())
    val state: StateFlow<PopularMoviesState> get() = _state
    private var page by mutableIntStateOf(1)

    suspend fun loadMovies() {
        try {
            _state.update {
                it.copy(
                    asyncState = AsyncState.LOADING,
                    paginationState = if (page == 1) PaginationState.LOADING else PaginationState.PAGINATING
                )
            }
            val result = repo.getPopularMovies(page)
            _state.update {
                it.copy(
                    asyncState = AsyncState.SUCCESS,
                    popularMovies = it.popularMovies + result,
                    paginationState = result.run {
                        if (size < PAGE_SIZE) PaginationState.PAGINATION_EXHAUSTED
                        else PaginationState.IDLE
                    })
            }
            page++
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    asyncState = AsyncState.ERROR,
                    error = e,
                    paginationState = PaginationState.ERROR
                )
            }
        }
    }

}


data class PopularMoviesState(
    val asyncState: AsyncState = AsyncState.INITIAL,
    val popularMovies: List<Movie> = emptyList(),
    val paginationState: PaginationState = PaginationState.IDLE,
    val error: Throwable? = null,
)

enum class AsyncState {
    INITIAL,
    LOADING,
    SUCCESS,
    ERROR;

    val isLoading: Boolean get() = this == LOADING
    val isSuccess: Boolean get() = this == SUCCESS
    val isError: Boolean get() = this == ERROR
    val isInitial: Boolean get() = this == INITIAL
}




