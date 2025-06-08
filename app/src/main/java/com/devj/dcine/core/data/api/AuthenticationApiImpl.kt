package com.devj.dcine.core.data.api

import com.devj.dcine.core.data.api.dtos.auth.AccountDto
import com.devj.dcine.core.data.api.dtos.auth.CreateSessionRequest
import com.devj.dcine.core.data.api.dtos.auth.CreateSessionResponseDto
import com.devj.dcine.core.data.api.dtos.auth.RequestTokenDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path

class AuthenticationApiImpl(private val client: HttpClient) : AuthenticationApi {
    override suspend fun createRequestToken(): RequestTokenDto {
        return client.get {
            url {
                path("authentication/token/new")
            }
        }.body()
    }

    override suspend fun createSession(requestToken: String): CreateSessionResponseDto {
        return client.post {
            url {
                path("authentication/session/new")
                setBody(CreateSessionRequest(requestToken))
            }
        }.body()
    }

    override suspend fun me(sessionId: String): AccountDto {
        return  client.get {
            url {
                path("account")
                parameters.append("session_id", sessionId)
            }

        }.body()
    }
}