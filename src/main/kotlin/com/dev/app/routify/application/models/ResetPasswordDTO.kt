package com.dev.app.routify.application.models

data class ResetPasswordDTO(
    val email: String,
    val hashcode: String,
    val password: String
)
