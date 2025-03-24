package com.devj.dcine

import android.app.Application
import com.devj.dcine.core.data.api.di.networkModule
import com.devj.dcine.core.di.coreModule
import com.devj.dcine.features.detail.di.movieDetailModule
import com.devj.dcine.features.movies.di.moviesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(coreModule, networkModule, moviesModule, movieDetailModule)
        }
    }
}