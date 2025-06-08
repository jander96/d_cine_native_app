package com.devj.dcine.features.auth.presenter

import com.devj.dcine.features.auth.domain.models.Account


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.features.auth.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(private val repository: AuthenticationRepository) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Initial)
    val state get() = _state.asStateFlow()


    init {
        viewModelScope.launch {
            _state.update { State.Loading }
            try {
                val account = repository.me()
                _state.update { State.Success(account) }
            } catch (e: Exception) {
               _state.update { State.Error(e) }
            }
        }
    }


    sealed class State {
        data object Initial : State()
        data object Loading : State()
        data class Success(val account: Account) : State()
        data class Error(val error: Throwable) : State()
    }

}