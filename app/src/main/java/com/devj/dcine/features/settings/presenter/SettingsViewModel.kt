package com.devj.dcine.features.settings.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.features.settings.domain.models.AppSetting
import com.devj.dcine.features.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {
    private val _state: MutableStateFlow<AppSetting> = MutableStateFlow(AppSetting())
    val state: StateFlow<AppSetting>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.settingsFlow().collectLatest { settings ->
                _state.update { settings }
            }
        }
    }

    fun changeLanguage(language: String) {
        viewModelScope.launch {
            repository.setSettings(_state.value.copy(language = language))
        }
    }

    fun changeThemeMode(isDark: Boolean) {
        viewModelScope.launch {
            repository.setSettings(_state.value.copy(isDark = isDark))
        }
    }
}