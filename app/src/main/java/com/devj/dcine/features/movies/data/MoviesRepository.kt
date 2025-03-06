package com.devj.dcine.features.movies.data

import com.devj.dcine.features.movies.domain.Movie

interface MoviesRepository {
    suspend fun getPopularMovies(page: Int): List<Movie>
}