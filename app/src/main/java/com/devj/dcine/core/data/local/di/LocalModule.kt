package com.devj.dcine.core.data.local.di

import androidx.room.Room
import com.devj.dcine.core.data.local.database.MovieDatabase
import com.devj.dcine.core.data.local.database.dao.MovieDetailDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single<MovieDatabase> {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "movie_database"
        ).build()
    }
    single<MovieDetailDao> { get<MovieDatabase>().movieDetailDao() }
}