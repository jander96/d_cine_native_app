package com.devj.dcine.features.auth.di

import com.devj.dcine.features.auth.data.repository.AuthenticationRepositoryImpl
import com.devj.dcine.features.auth.domain.repository.AuthenticationRepository
import com.devj.dcine.features.auth.presenter.AuthenticationViewModel
import com.devj.dcine.features.auth.presenter.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            context = androidContext(),
            api = get()
        )
    }

    viewModel {
        AuthenticationViewModel(get())
    }

    viewModel {
        UserViewModel(get())
    }
}