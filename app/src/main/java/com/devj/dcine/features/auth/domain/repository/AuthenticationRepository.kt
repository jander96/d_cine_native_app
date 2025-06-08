package com.devj.dcine.features.auth.domain.repository

import com.devj.dcine.features.auth.domain.models.Account
import com.devj.dcine.features.auth.domain.models.CreateSessionResponse
import com.devj.dcine.features.auth.domain.models.RequestToken
import kotlinx.coroutines.flow.Flow


interface AuthenticationRepository {
    fun currentSession(): Flow<String?>
    suspend fun createRequestToken(): RequestToken
    suspend fun createSession(token: String): CreateSessionResponse
    suspend fun me(): Account
}