package com.dev.app.routify.application.models

data class CreateUserDTO(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String?,
    val phoneNumber: String
)
