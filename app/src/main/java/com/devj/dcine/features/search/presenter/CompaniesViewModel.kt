package com.devj.dcine.features.search.presenter

import androidx.lifecycle.ViewModel
import com.devj.dcine.features.detail.domain.ProductionCompany

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.devj.dcine.core.composables.PaginationState
import com.devj.dcine.core.presenter.AsyncState
import com.devj.dcine.features.movies.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CompaniesViewModel(private val repo: MoviesRepository) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
    }

    private val _state = MutableStateFlow(CompaniesState())
    val state: StateFlow<CompaniesState> get() = _state
    private var page by mutableIntStateOf(1)

    suspend fun searchCompanyByName(query: String, isRefresh: Boolean = false) {
        if(isRefresh) {
            page = 1
            _state.update { CompaniesState() }
        }
        try {
            _state.update {
                it.copy(
                    asyncState = AsyncState.LOADING,
                    paginationState = if (page == 1) PaginationState.LOADING else PaginationState.PAGINATING
                )
            }
            val result = repo.getCompanies(query,page)
            _state.update {
                it.copy(
                    asyncState = AsyncState.SUCCESS,
                    companies = it.companies + result,
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


data class CompaniesState(
    val asyncState: AsyncState = AsyncState.INITIAL,
    val companies: List<ProductionCompany> = emptyList(),
    val paginationState: PaginationState = PaginationState.IDLE,
    val error: Throwable? = null,
)






