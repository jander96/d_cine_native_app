package com.devj.dcine.core.data.api.di

import com.devj.dcine.BuildConfig
import com.devj.dcine.core.data.api.MovieApi
import com.devj.dcine.core.data.api.MovieApiImpl
import com.devj.dcine.core.data.api.localizationInterceptorPlugin
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(OkHttp) {


            install(localizationInterceptorPlugin(get()))


            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    path("3/")
                    parameters.append("api_key", BuildConfig.MOVIE_API_KEY)
                }
                headers.append("Content-Type", "application/json")
            }
            install(ContentNegotiation) {
                json(Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpRequestRetry) {
                maxRetries = 5
                retryIf { request, response ->
                    !response.status.isSuccess()
                }
                retryOnExceptionIf { request, cause ->
                    false
                }
                delayMillis { retry ->
                    retry * 3000L
                } // retries in 3, 6, 9, etc. seconds
            }
            install(Logging) // Plugin for log network traffic

        }
    }

    single<MovieApi> {
        MovieApiImpl(get())
    }

}
