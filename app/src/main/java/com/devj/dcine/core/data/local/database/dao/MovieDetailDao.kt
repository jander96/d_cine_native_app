package com.devj.dcine.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.devj.dcine.core.data.local.database.entities.GenreEntity
import com.devj.dcine.core.data.local.database.entities.MovieDetailEntity
import com.devj.dcine.core.data.local.database.entities.MovieDetailWithRelations
import com.devj.dcine.core.data.local.database.entities.MovieGenreCrossRef
import com.devj.dcine.core.data.local.database.entities.OriginCountryEntity
import com.devj.dcine.core.data.local.database.entities.ProductionCompanyEntity
import com.devj.dcine.core.data.local.database.entities.ProductionCountryEntity
import com.devj.dcine.core.data.local.database.entities.SpokenLanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailDao {

    // =====================
    // INSERT (CREATE)
    // =====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movie: MovieDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenreCrossRefs(refs: List<MovieGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCompanies(companies: List<ProductionCompanyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCountries(countries: List<ProductionCountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpokenLanguages(languages: List<SpokenLanguageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOriginCountries(countries: List<OriginCountryEntity>)

    // =====================
    // READ (CONSULTA)
    // =====================

    @Transaction
    @Query("SELECT * FROM movie_detail WHERE id = :movieId")
    suspend fun getMovieDetailWithRelations(movieId: Int): MovieDetailWithRelations?

    @Query("SELECT * FROM movie_detail")
    suspend fun getAllMovieDetails(): List<MovieDetailEntity>

    @Query("SELECT * FROM genre")
    suspend fun getAllGenres(): List<GenreEntity>

    @Query("SELECT * FROM production_company WHERE movieId = :movieId")
    suspend fun getProductionCompaniesByMovie(movieId: Int): List<ProductionCompanyEntity>

    @Query("SELECT * FROM production_country WHERE movieId = :movieId")
    suspend fun getProductionCountriesByMovie(movieId: Int): List<ProductionCountryEntity>

    @Query("SELECT * FROM spoken_language WHERE movieId = :movieId")
    suspend fun getSpokenLanguagesByMovie(movieId: Int): List<SpokenLanguageEntity>

    @Query("SELECT * FROM origin_country WHERE movieId = :movieId")
    suspend fun getOriginCountriesByMovie(movieId: Int): List<OriginCountryEntity>

    @Transaction
    @Query("SELECT * FROM movie_detail ORDER BY sawAt DESC LIMIT 20")
    fun observeAllMoviesWithRelations(): Flow<List<MovieDetailWithRelations>>

    // =====================
    // UPDATE (ACTUALIZAR)
    // =====================

    @Update
    suspend fun updateMovieDetail(movie: MovieDetailEntity)

    @Update
    suspend fun updateProductionCompanies(companies: List<ProductionCompanyEntity>)

    @Update
    suspend fun updateProductionCountries(countries: List<ProductionCountryEntity>)

    @Update
    suspend fun updateSpokenLanguages(languages: List<SpokenLanguageEntity>)

    @Update
    suspend fun updateOriginCountries(countries: List<OriginCountryEntity>)

    // =====================
    // DELETE (ELIMINAR)
    // =====================

    @Delete
    suspend fun deleteMovieDetail(movie: MovieDetailEntity)

    @Query("DELETE FROM genre WHERE id = :genreId")
    suspend fun deleteGenreById(genreId: Int)

    @Query("DELETE FROM movie_genres WHERE movieId = :movieId")
    suspend fun deleteMovieGenreCrossRefs(movieId: Int)

    @Query("DELETE FROM production_company WHERE movieId = :movieId")
    suspend fun deleteProductionCompaniesByMovie(movieId: Int)

    @Query("DELETE FROM production_country WHERE movieId = :movieId")
    suspend fun deleteProductionCountriesByMovie(movieId: Int)

    @Query("DELETE FROM spoken_language WHERE movieId = :movieId")
    suspend fun deleteSpokenLanguagesByMovie(movieId: Int)

    @Query("DELETE FROM origin_country WHERE movieId = :movieId")
    suspend fun deleteOriginCountriesByMovie(movieId: Int)
}