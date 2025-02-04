package com.dev.app.routify.application.models

data class AuthDTO(
    val token: String,
    val typeToken: String,
    val expirationTime: String
)
