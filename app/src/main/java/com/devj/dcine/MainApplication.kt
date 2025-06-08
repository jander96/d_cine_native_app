package com.devj.dcine

import android.app.Application
import com.devj.dcine.core.data.api.di.networkModule
import com.devj.dcine.core.data.local.di.localModule
import com.devj.dcine.core.di.coreModule
import com.devj.dcine.features.auth.di.authModule
import com.devj.dcine.features.detail.di.movieDetailModule
import com.devj.dcine.features.filters.di.filtersModule
import com.devj.dcine.features.home.di.homeModule
import com.devj.dcine.features.movies.di.moviesModule
import com.devj.dcine.features.search.di.searchModule
import com.devj.dcine.features.settings.di.settingsModule
import com.devj.dcine.features.splash.di.splashModule
import com.devj.dcine.features.video.di.videoModule
import io.kotzilla.sdk.analytics.koin.analytics
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.request.url
import io.ktor.http.URLBuilder
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            analytics()
            modules(
                coreModule, networkModule, moviesModule, movieDetailModule, videoModule,
                filtersModule, searchModule, homeModule, localModule, settingsModule, splashModule,
                authModule
            )

        }
    }
}