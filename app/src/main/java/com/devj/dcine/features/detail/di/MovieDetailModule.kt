package com.devj.dcine.features.detail.di

import com.devj.dcine.features.detail.presenter.MovieDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val movieDetailModule = module {
    viewModel { MovieDetailViewModel(get()) }
}