package com.dev.app.routify.infrastructure.event.models

import java.util.*

data class UserScopesEventDTO(
    val transaction: UUID,
    val userId: Long,
    val scopeKey: String
)
