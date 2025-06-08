package com.devj.dcine.core.data.api

import com.devj.dcine.core.data.api.dtos.auth.AccountDto
import com.devj.dcine.core.data.api.dtos.auth.CreateSessionResponseDto
import com.devj.dcine.core.data.api.dtos.auth.RequestTokenDto

interface AuthenticationApi {
    suspend fun createRequestToken(): RequestTokenDto
    suspend fun createSession(requestToken: String): CreateSessionResponseDto
    suspend fun me(sessionId: String): AccountDto
}