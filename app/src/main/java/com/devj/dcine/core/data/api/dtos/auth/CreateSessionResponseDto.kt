package com.devj.dcine.core.data.api.dtos.auth

import com.devj.dcine.features.auth.domain.models.CreateSessionResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionResponseDto(
    val success: Boolean,
    @SerialName("session_id") val sessionId: String
) {
    fun toDomain() = CreateSessionResponse(success,sessionId)
}