package com.devj.dcine.features.auth.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.devj.dcine.core.data.api.AuthenticationApi
import com.devj.dcine.core.data.local.datastore.dataStore
import com.devj.dcine.features.auth.domain.models.Account
import com.devj.dcine.features.auth.domain.models.CreateSessionResponse
import com.devj.dcine.features.auth.domain.models.RequestToken
import com.devj.dcine.features.auth.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class AuthenticationRepositoryImpl(private val context: Context,private val api: AuthenticationApi) : AuthenticationRepository {
    companion object {
        val SESSION_KEY = stringPreferencesKey("session_id")
    }
    override fun currentSession(): Flow<String?> {
        return  context.dataStore.data.map {
            it[SESSION_KEY]
        }
    }

    override suspend fun createRequestToken(): RequestToken {
        return api.createRequestToken().toDomain()
    }

    override suspend fun createSession(token: String): CreateSessionResponse {
        val session = api.createSession(token)
        context.dataStore.edit { preferences->
            preferences[SESSION_KEY]= session.sessionId
        }
        return session.toDomain()
    }

    override suspend fun me(): Account {
        val sessionId = context.dataStore.data.first()[SESSION_KEY]
        if(sessionId.isNullOrBlank()) throw Exception("no saved session")
        return api.me(sessionId).toDomain()
    }

}