package com.devj.dcine.features.movies.presenter


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.devj.dcine.core.composables.PaginationState
import com.devj.dcine.core.presenter.AsyncState
import com.devj.dcine.core.presenter.MovieStateI
import com.devj.dcine.features.movies.data.MoviesRepository
import com.devj.dcine.features.movies.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TopRatedMoviesViewModel(private val repo: MoviesRepository) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
    }

    private val _state = MutableStateFlow(TopRatedMoviesState())
    val state: StateFlow<TopRatedMoviesState> get() = _state
    private var page by mutableIntStateOf(1)

    suspend fun loadMovies(isRefresh: Boolean = false) {
        if(isRefresh) {
            page = 1
            _state.update { TopRatedMoviesState() }
        }
        try {
            _state.update {
                it.copy(
                    asyncState = AsyncState.LOADING,
                    paginationState = if (page == 1) PaginationState.LOADING else PaginationState.PAGINATING
                )
            }
            val result = repo.getTopRatedMovies(page)
            _state.update {
                it.copy(
                    asyncState = AsyncState.SUCCESS,
                    movies = it.movies + result,
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


data class TopRatedMoviesState(
    override val asyncState: AsyncState = AsyncState.INITIAL,
    override val movies: List<Movie> = emptyList(),
    override val paginationState: PaginationState = PaginationState.IDLE,
    override val error: Throwable? = null,
): MovieStateI






