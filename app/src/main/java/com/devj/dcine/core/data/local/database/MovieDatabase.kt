package com.devj.dcine.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devj.dcine.core.data.local.database.dao.MovieDetailDao
import com.devj.dcine.core.data.local.database.entities.GenreEntity
import com.devj.dcine.core.data.local.database.entities.MovieDetailEntity
import com.devj.dcine.core.data.local.database.entities.MovieGenreCrossRef
import com.devj.dcine.core.data.local.database.entities.OriginCountryEntity
import com.devj.dcine.core.data.local.database.entities.ProductionCompanyEntity
import com.devj.dcine.core.data.local.database.entities.ProductionCountryEntity
import com.devj.dcine.core.data.local.database.entities.SpokenLanguageEntity

@Database(
    entities = [
        MovieDetailEntity::class,
        MovieGenreCrossRef::class,
        GenreEntity::class,
        ProductionCompanyEntity::class,
        ProductionCountryEntity::class,
        SpokenLanguageEntity::class,
        OriginCountryEntity::class,
    ],
    version = 1,
    exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDetailDao(): MovieDetailDao
}