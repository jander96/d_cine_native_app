package com.devj.dcine.features.splash.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.features.search.presenter.SearchViewModel.SearchUiEvent
import com.devj.dcine.features.settings.domain.models.AppSetting
import com.devj.dcine.features.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel(private val settingsRepository: SettingsRepository): ViewModel() {
    init {
        setDefaultSettings()
    }
    sealed class UiEvent {
        data object SetDefaultSetting: UiEvent()
        data object PreviousSettings: UiEvent()
    }
    private val _events = Channel<UiEvent>()
    val events = _events.receiveAsFlow()

    fun setDefaultSettings() {
        viewModelScope.launch {
            if(settingsRepository.getCurrentSettings().isEmpty){
                settingsRepository.setSettings(
                    AppSetting()
                )
                _events.send(UiEvent.SetDefaultSetting)
            }else {
                _events.send(UiEvent.PreviousSettings)
            }
        }
    }
}