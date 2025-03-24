package com.devj.dcine.features.movies.data

import com.devj.dcine.core.data.api.MovieApi
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.movies.domain.Movie

class MoviesRepositoryImpl(private val api: MovieApi) : MoviesRepository {

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val response = api.getPopularMovies(page = page)
        return  response.results.map { it.toDomain() }
    }

    override suspend fun getMovie(id: Int): MovieDetail {
        val response = api.getMovie(id)
        return  response.toDomain()
    }

}