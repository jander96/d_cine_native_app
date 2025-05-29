package com.devj.dcine.features.search.presenter

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.core.composables.PaginationState
import com.devj.dcine.core.presenter.AsyncState
import com.devj.dcine.core.presenter.MovieStateI
import com.devj.dcine.features.filters.domain.models.MovieFilter
import com.devj.dcine.features.filters.domain.repository.FilterRepository
import com.devj.dcine.features.movies.data.MoviesRepository
import com.devj.dcine.features.movies.domain.Movie
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: MoviesRepository, filterRepository: FilterRepository) :
    ViewModel() {

    sealed class SearchUiEvent {
        data class Search(val query: String) : SearchUiEvent()
        data class FilterChanged(val filter: MovieFilter) : SearchUiEvent()
    }

    companion object {
        const val PAGE_SIZE = 20
    }
    private var page: MutableIntState = mutableIntStateOf(1)
    private var filters = MovieFilter()
    private val _events = Channel<SearchUiEvent>()
    val events = _events.receiveAsFlow()


    init {
        viewModelScope.launch {
            filterRepository.getMovieFilters().collectLatest { filter ->
                filters = filter
                _events.send(SearchUiEvent.FilterChanged(filter))
            }
        }
    }

    private val _state = MutableStateFlow(SearchMoviesState())
    val state: StateFlow<SearchMoviesState> get() = _state


    suspend fun loadMovies(isRefresh: Boolean = false) {
        if (isRefresh) {
            page.intValue = 1
            _state.update { SearchMoviesState() }
        }
        try {
            _state.update {
                it.copy(
                    asyncState = AsyncState.LOADING,
                    paginationState = if (page.intValue == 1) PaginationState.LOADING else PaginationState.PAGINATING
                )
            }
            val result = repo.discoverMovies(filter = filters, page = page.intValue)
            _state.update {
                it.copy(
                    asyncState = AsyncState.SUCCESS,
                    movies = if(isRefresh) it.movies else it.movies + result,
                    paginationState = result.run {
                        if (size < PAGE_SIZE) PaginationState.PAGINATION_EXHAUSTED
                        else PaginationState.IDLE
                    })
            }
            page.intValue++
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



data class SearchMoviesState(
    override val asyncState: AsyncState = AsyncState.INITIAL,
    override val movies: List<Movie> = emptyList(),
    override val paginationState: PaginationState = PaginationState.IDLE,
    override val error: Throwable? = null,
) : MovieStateI






