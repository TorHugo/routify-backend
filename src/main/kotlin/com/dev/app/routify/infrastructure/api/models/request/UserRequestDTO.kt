package com.dev.app.routify.infrastructure.api.models.request

import com.dev.app.routify.domain.annotation.Password
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class UserRequestDTO(
    @NotBlank
    val email: String,
    @Password
    @NotBlank
    val password: String,
    @NotBlank
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String?,
    @NotBlank
    @JsonProperty("phone_number")
    val phoneNumber: String
)
