package com.devj.dcine.features.movies.data

import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.movies.domain.Movie

interface MoviesRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getMovie(id: Int): MovieDetail
}