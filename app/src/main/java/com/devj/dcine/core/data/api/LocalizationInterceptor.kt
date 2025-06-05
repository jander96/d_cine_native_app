package com.devj.dcine.core.data.api

import com.devj.dcine.features.settings.domain.repository.SettingsRepository
import io.ktor.client.plugins.api.createClientPlugin
import okhttp3.Interceptor
import okhttp3.Response

class LocalizationInterceptor(private val language: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("language", language)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}


fun localizationInterceptorPlugin(settingsRepository: SettingsRepository) = createClientPlugin("LocalizationInterceptor") {

    onRequest { request, _ ->
        val language = settingsRepository.getCurrentSettings()
        request.url.parameters.append("language", language.language)
    }
}