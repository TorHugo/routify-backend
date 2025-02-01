package com.dev.app.routify.infrastructure.api.models.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class UserResponseDTO(
    @JsonProperty("user_id")
    val userExternalId: UUID
)
