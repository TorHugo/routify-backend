package com.dev.app.routify.infrastructure.api.models.response

import com.fasterxml.jackson.annotation.JsonProperty

data class BasicLoginResponseDTO(
    @JsonProperty("basic_token")
    val basicToken: String,
    @JsonProperty("token_type")
    val tokenType: String? = null,
    @JsonProperty("expiration_date")
    val expirationDate: String? = null
)
