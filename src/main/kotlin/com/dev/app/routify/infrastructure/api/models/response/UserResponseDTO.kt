package com.dev.app.routify.infrastructure.api.models.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class UserResponseDTO(
    @JsonProperty("user_id")
    val externalId: UUID? = null,
    @JsonProperty("email")
    val email: String? = null,
    @JsonProperty("first_name")
    val firstName: String? = null,
    @JsonProperty("last_name")
    val lastName: String? = null,
    @JsonProperty("phone_number")
    val phoneNumber: String? = null,
    @JsonProperty("active")
    val active: Boolean = true,
    @JsonProperty("confirmed")
    val confirmed: Boolean = false,
    @JsonProperty("created_at")
    val createdAt: String? = null,
    @JsonProperty("updated_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val updatedAt: String? = null
)
