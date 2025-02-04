package com.dev.app.routify.application.models

import java.time.LocalDateTime

data class HashDTO(
    val identifier: String,
    val expiration: LocalDateTime
)
