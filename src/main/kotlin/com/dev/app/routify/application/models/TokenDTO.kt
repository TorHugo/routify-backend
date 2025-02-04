package com.dev.app.routify.application.models

import java.time.LocalDateTime

data class TokenDTO(
    val identifier: Long? = null,
    val userId: Long,
    val tokenHash: String,
    val tokenType: String,
    val expiration: LocalDateTime
)
