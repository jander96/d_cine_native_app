package com.devj.dcine.features.settings.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.devj.dcine.AppSettingProto
import com.devj.dcine.core.data.local.datastore.dataStore
import com.devj.dcine.core.data.local.datastore.settingsDataStore
import com.devj.dcine.features.settings.domain.models.AppSetting
import com.devj.dcine.features.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {
    companion object {
        private val REMEMBER_ME = booleanPreferencesKey("rememberMe")
    }

    override suspend fun rememberMe(value: Boolean) {
        context.dataStore.edit { settings ->
            settings[REMEMBER_ME] = value
        }
    }

    override fun rememberUser(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[REMEMBER_ME] == true
            }
    }

    override fun settingsFlow(): Flow<AppSetting> {
        return context.settingsDataStore.data.map {
            AppSetting(
                language = it.language,
                region = it.region,
                isDark = it.isDark,
            )

        }
    }

    override suspend fun setSettings(setting: AppSetting) {
        context.settingsDataStore.updateData {
            it.toBuilder()
                .setLanguage(setting.language)
                .setRegion(setting.region)
                .setIsDark(setting.isDark)
                .build()
        }
    }

    override suspend fun getCurrentSettings(): AppSetting {
        return context.settingsDataStore.data.first().toDomain()
    }
}

fun AppSettingProto.toDomain() : AppSetting {
    return AppSetting(
        language = language,
        region = region,
        isDark = isDark,
    )
}