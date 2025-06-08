package com.devj.dcine.core.data.api.dtos.auth

import com.devj.dcine.features.auth.domain.models.RequestToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenDto(
    val success: Boolean,
    @SerialName("expires_at") val expiresAt: String,
    @SerialName("request_token") val requestToken: String
){
    fun toDomain() = RequestToken(success,expiresAt,requestToken)
}
