package com.devj.dcine.features.splash.di

import com.devj.dcine.features.splash.presenter.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel {
        SplashViewModel(settingsRepository = get())
    }
}