package com.dev.app.routify.application.models

data class UpdateUserPasswordDTO(
    val matches: Boolean,
    val oldPassword: String,
    val newPassword: String
)
