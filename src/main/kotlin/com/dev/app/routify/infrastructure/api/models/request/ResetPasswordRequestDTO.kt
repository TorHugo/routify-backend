package com.dev.app.routify.infrastructure.api.models.request

import com.dev.app.routify.domain.annotation.Password
import com.fasterxml.jackson.annotation.JsonProperty

data class ResetPasswordRequestDTO(
    @JsonProperty("hashcode")
    val hash: String,
    @Password
    val newPassword: String
)
