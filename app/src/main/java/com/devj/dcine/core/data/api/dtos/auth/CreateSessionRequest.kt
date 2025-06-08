package com.devj.dcine.core.data.api.dtos.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionRequest(
    @SerialName("request_token")
    val requestToken: String
)
