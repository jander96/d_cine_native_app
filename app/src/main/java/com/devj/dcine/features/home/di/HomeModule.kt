package com.devj.dcine.features.home.di

import com.devj.dcine.features.home.presenter.HistoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HistoryViewModel(repo = get()) }
}