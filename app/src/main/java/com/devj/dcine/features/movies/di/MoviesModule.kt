package com.devj.dcine.features.movies.di

import com.devj.dcine.features.movies.data.MoviesRepository
import com.devj.dcine.features.movies.data.MoviesRepositoryImpl
import com.devj.dcine.features.movies.presenter.NowPlayingMoviesViewModel
import com.devj.dcine.features.movies.presenter.PopularMoviesViewModel
import com.devj.dcine.features.movies.presenter.TopRatedMoviesViewModel
import com.devj.dcine.features.movies.presenter.UpcomingMoviesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    single<MoviesRepository> {  MoviesRepositoryImpl(api = get(), dao =  get()) }
    viewModel { PopularMoviesViewModel(get()) }
    viewModel { TopRatedMoviesViewModel(get()) }
    viewModel { NowPlayingMoviesViewModel(get()) }
    viewModel { UpcomingMoviesViewModel(get()) }
}