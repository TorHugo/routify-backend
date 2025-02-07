package com.dev.app.routify.infrastructure.api.models.request

data class AuthLoginRequestDTO(
    val email: String,
    val password: String
)
