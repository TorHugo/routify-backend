package com.dev.app.routify.infrastructure.api.models.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UserConfirmationDTO(
    @JsonProperty("hashcode")
    val hash: String
)
