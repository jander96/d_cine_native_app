package com.devj.dcine.features.filters.di

import com.devj.dcine.features.filters.data.repository.FilterRepositoryImp
import com.devj.dcine.features.filters.domain.repository.FilterRepository
import com.devj.dcine.features.filters.presenter.MovieFilterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val filtersModule = module {
    single<FilterRepository> { FilterRepositoryImp() }
    viewModel { MovieFilterViewModel(repo = get()) }
}