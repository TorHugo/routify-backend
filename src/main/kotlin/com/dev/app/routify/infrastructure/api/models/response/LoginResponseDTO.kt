package com.dev.app.routify.infrastructure.api.models.response

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponseDTO(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("token_type")
    val tokenType: String? = null,
    @JsonProperty("expiration_date")
    val expirationDate: String? = null
)
