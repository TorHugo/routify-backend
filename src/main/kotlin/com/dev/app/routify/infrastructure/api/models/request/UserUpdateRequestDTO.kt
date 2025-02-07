package com.dev.app.routify.infrastructure.api.models.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UserUpdateRequestDTO(
    @JsonProperty("first_name")
    val firstName: String?,
    @JsonProperty("last_name")
    val lastName: String?,
    @JsonProperty("phone_number")
    val phoneNumber: String?
)
