package com.devj.dcine.features.settings.di

import com.devj.dcine.features.settings.data.repository.SettingsRepositoryImpl
import com.devj.dcine.features.settings.domain.repository.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsRepository> {
        SettingsRepositoryImpl(context = androidContext())
    }
}