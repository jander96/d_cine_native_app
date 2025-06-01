package com.devj.dcine.features.detail.di

import com.devj.dcine.features.actors.presenter.ActorsViewModel
import com.devj.dcine.features.detail.presenter.MovieDetailViewModel
import com.devj.dcine.features.detail.presenter.SimilarMoviesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val movieDetailModule = module {
    viewModel { MovieDetailViewModel(get()) }
    viewModel { (id: Int)->
        SimilarMoviesViewModel(id = id, repo = get())
    }
}