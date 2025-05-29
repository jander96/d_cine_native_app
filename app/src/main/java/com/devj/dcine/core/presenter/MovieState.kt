package com.devj.dcine.core.presenter

import com.devj.dcine.core.composables.PaginationState
import com.devj.dcine.features.movies.domain.Movie

interface MovieStateI {
    val asyncState: AsyncState
    val movies: List<Movie>
    val paginationState: PaginationState
    val error: Throwable?
}