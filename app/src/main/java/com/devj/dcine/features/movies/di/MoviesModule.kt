package com.devj.dcine.features.movies.di

import com.devj.dcine.features.movies.data.MoviesRepository
import com.devj.dcine.features.movies.data.MoviesRepositoryImpl
import com.devj.dcine.features.movies.presenter.PopularMoviesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    factory<MoviesRepository> {  MoviesRepositoryImpl(get()) }
    viewModel { PopularMoviesViewModel(get()) }
}