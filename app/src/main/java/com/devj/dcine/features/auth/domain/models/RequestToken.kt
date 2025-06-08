package com.devj.dcine.features.auth.domain.models


data class RequestToken(
    val success: Boolean,
    val expiresAt: String,
    val requestToken: String
)
