package com.dev.app.routify.infrastructure.api.models.request

data class UserConfirmationDTO(
    val email: String,
    val hashcode: String
)
