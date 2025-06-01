package com.devj.dcine.features.settings.domain.repository

import com.devj.dcine.features.settings.domain.models.AppSetting
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun rememberMe(value: Boolean)
    fun rememberUser():Flow<Boolean>
    fun settingsFlow(): Flow<AppSetting>
    suspend fun setSettings(setting: AppSetting)
    suspend fun getCurrentSettings(): AppSetting
}