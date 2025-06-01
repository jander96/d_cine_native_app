package com.devj.dcine.features.search.di

import com.devj.dcine.features.search.presenter.CompaniesViewModel
import com.devj.dcine.features.search.presenter.GenresViewModel
import com.devj.dcine.features.search.presenter.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    viewModel { SearchViewModel(repo = get(), filterRepository = get()) }
    viewModel { GenresViewModel(repo = get()) }
    viewModel { CompaniesViewModel(repo = get()) }
}