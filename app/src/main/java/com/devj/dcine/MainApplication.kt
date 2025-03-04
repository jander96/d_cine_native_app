package com.devj.dcine

import android.app.Application
import com.devj.dcine.core.data.networkModule
import com.devj.dcine.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(coreModule, networkModule)
        }
    }
}