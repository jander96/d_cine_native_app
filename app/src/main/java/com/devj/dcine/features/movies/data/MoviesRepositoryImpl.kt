package com.devj.dcine.features.movies.data

import androidx.room.Transaction
import com.devj.dcine.core.data.api.MovieApi
import com.devj.dcine.core.data.local.database.dao.MovieDetailDao
import com.devj.dcine.core.data.local.database.entities.MovieDetailWithRelations
import com.devj.dcine.core.data.local.database.entities.MovieGenreCrossRef
import com.devj.dcine.features.detail.domain.Actor
import com.devj.dcine.features.detail.domain.Genre
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.detail.domain.ProductionCompany
import com.devj.dcine.features.filters.domain.models.MovieFilter
import com.devj.dcine.features.filters.domain.models.SortBy
import com.devj.dcine.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.Stack

class MoviesRepositoryImpl(private val api: MovieApi, private val dao: MovieDetailDao) : MoviesRepository {


    override fun getLastSeenMovies(): Flow<List<MovieDetail>> {
        return dao.observeAllMoviesWithRelations().map { list-> list.map { it.toDomain() }  }
    }

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val response = api.getPopularMovies(page = page)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        val response = api.getTopRatedMovies(page = page)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        val response = api.getNowPlayingMovies(page = page)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getSimilarMovies(id: Int,page: Int): List<Movie> {
        return  api.getSimilarMovies(id,page).results.map { it.toDomain() }
    }

    override suspend fun discoverMovies(
        filter: MovieFilter,
        page: Int,
        order: SortBy
    ): List<Movie> {
        val response = api.discoverMovies(
            filter = filter,
            page = page,
            order = order
        )
        return response.results.map { it.toDomain() }
    }

    override suspend fun getUpcomingMovies(page: Int): List<Movie> {
        val response = api.getUpcomingMovies(page = page)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getMovie(id: Int): MovieDetail {
        val response = api.getMovie(id)
        saveMovie(response.toDomain())
        return response.toDomain()
    }

    override suspend fun getCast(id: Int): List<Actor> {
        val response = api.getCasting(id)
        return response.toDomain()
    }
    @Transaction
    override suspend fun saveMovie(movie: MovieDetail) {

            val movieWithRelations = MovieDetailWithRelations(movie)
            val movie = movieWithRelations.movie

            // 1. Insertar detalle principal
            dao.insertMovieDetail(movie)

            // 2. Insertar géneros y referencias cruzadas
            val genres = movieWithRelations.genres
            dao.insertGenres(genres)

            val genreRefs = genres.map {
                MovieGenreCrossRef(movieId = movie.id, genreId = it.id)
            }
            dao.insertMovieGenreCrossRefs(genreRefs)

            // 3. Insertar compañías
            val companies = movieWithRelations.productionCompanies.map {
                it.copy(movieId = movie.id)
            }
            dao.insertProductionCompanies(companies)

            // 4. Insertar países de producción
            val countries = movieWithRelations.productionCountries.map {
                it.copy(movieId = movie.id)
            }
            dao.insertProductionCountries(countries)

            // 5. Insertar idiomas hablados
            val languages = movieWithRelations.spokenLanguages.map {
                it.copy(movieId = movie.id)
            }
            dao.insertSpokenLanguages(languages)

            // 6. Insertar países de origen
            val originCountries = movieWithRelations.originCountries.map {
                it.copy(movieId = movie.id)
            }
            dao.insertOriginCountries(originCountries)

    }

    override suspend fun deleteMovie(movie: MovieDetail) {

        @Transaction
        suspend fun deleteFullMovie(movieId: Int) {
            val movie = dao.getMovieDetailWithRelations(movieId)
                ?: return // Si no existe, salimos

            // Eliminar relaciones primero
            dao.deleteMovieGenreCrossRefs(movieId)
            dao.deleteProductionCompaniesByMovie(movieId)
            dao.deleteProductionCountriesByMovie(movieId)
            dao.deleteSpokenLanguagesByMovie(movieId)
            dao.deleteOriginCountriesByMovie(movieId)

            // Eliminar entidad principal
            dao.deleteMovieDetail(movie.movie)
        }
    }

    override suspend fun getGenres(): List<Genre> {
        return  api.getGenres().map { it.toDomain() }
    }

    override suspend fun getCompanies(
        query: String,
        page: Int
    ): List<ProductionCompany> {
        return  api.searchCompany(query,page).results.map { it.toDomain() }
    }

}