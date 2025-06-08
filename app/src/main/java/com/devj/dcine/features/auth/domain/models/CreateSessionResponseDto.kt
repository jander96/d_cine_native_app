package com.devj.dcine.features.auth.domain.models


data class CreateSessionResponse(
    val success: Boolean,
    val sessionId: String
)