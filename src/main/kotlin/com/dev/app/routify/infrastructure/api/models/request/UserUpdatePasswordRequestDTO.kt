package com.dev.app.routify.infrastructure.api.models.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UserUpdatePasswordRequestDTO(
    @JsonProperty("current_password")
    val currentPassword: String,
    @JsonProperty("new_password")
    val newPassword: String
)
