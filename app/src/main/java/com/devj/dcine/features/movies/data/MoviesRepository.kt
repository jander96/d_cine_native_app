package com.devj.dcine.features.movies.data

import com.devj.dcine.features.detail.domain.Actor
import com.devj.dcine.features.detail.domain.Genre
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.detail.domain.ProductionCompany
import com.devj.dcine.features.filters.domain.models.MovieFilter
import com.devj.dcine.features.filters.domain.models.SortBy
import com.devj.dcine.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getLastSeenMovies(): Flow<List<MovieDetail>>
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getTopRatedMovies(page: Int): List<Movie>
    suspend fun getNowPlayingMovies(page: Int): List<Movie>
    suspend fun getSimilarMovies(id: Int,page: Int): List<Movie>
    suspend fun discoverMovies(
        filter: MovieFilter,
        page: Int = 1,
        order: SortBy = SortBy.POPULARITY_DESC,
    ): List<Movie>
    suspend fun getUpcomingMovies(page: Int): List<Movie>
    suspend fun getMovie(id: Int): MovieDetail
    suspend fun getCast(id: Int): List<Actor>
    suspend fun saveMovie(movie: MovieDetail)
    suspend fun deleteMovie(movie: MovieDetail)
    suspend fun getGenres(): List<Genre>
    suspend fun getCompanies(query: String, page: Int): List<ProductionCompany>

}