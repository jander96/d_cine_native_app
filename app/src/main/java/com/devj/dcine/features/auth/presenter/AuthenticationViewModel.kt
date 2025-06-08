package com.devj.dcine.features.auth.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.features.auth.domain.models.CreateSessionResponse
import com.devj.dcine.features.auth.domain.models.RequestToken
import com.devj.dcine.features.auth.domain.repository.AuthenticationRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val repository: AuthenticationRepository) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Initial)
    val state get() = _state.asStateFlow()
    private val _events = Channel<UiEvent>()
    val event = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            try {
                val account = repository.me()
                val name  = account.name
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun requestToken() {
        viewModelScope.launch {
            try {
                _state.update { State.Loading }
                val token = repository.createRequestToken()
                _state.update { State.SuccessToken(token) }
                _events.send(UiEvent.SuccessToken(buildAuthUrl(token.requestToken)))
            } catch (e: Exception) {
                _state.update { State.Error(e) }
            }
        }
    }

    private fun buildAuthUrl(token: String): String {
        return "https://www.themoviedb.org/authenticate/$token?redirect_to=dcine://auth/callback"
    }

    fun createSession(token: String) {
        viewModelScope.launch {
            try {
                _state.update { State.Loading }
                val session = repository.createSession(token)
                _state.update { State.SuccessSession(session) }
                _events.send(UiEvent.SuccessSession(session.sessionId))
            } catch (e: Exception) {
                _state.update { State.Error(e) }
            }
        }
    }

    fun onAuthDenied() {
        _state.update { State.Error(Exception("AuthDenied")) }
    }

    sealed class State {
        data object Initial : State()
        data object Loading : State()
        data class SuccessToken(val token: RequestToken) : State()
        data class SuccessSession(val session: CreateSessionResponse) : State()
        data class Error(val error: Throwable) : State()
    }

    sealed class UiEvent {
        data object NoValidToken : UiEvent()
        data class SuccessToken(val url: String) : UiEvent()
        data class SuccessSession(val session: String) : UiEvent()
    }

}