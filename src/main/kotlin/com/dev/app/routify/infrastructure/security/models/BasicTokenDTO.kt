package com.dev.app.routify.infrastructure.security.models

import java.util.Date

data class BasicTokenDTO(
    val token: String,
    val type: String,
    val expirationDate: Date
)
