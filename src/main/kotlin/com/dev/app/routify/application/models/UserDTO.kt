package com.dev.app.routify.application.models

import java.time.LocalDateTime
import java.util.UUID

data class UserDTO(
    val identifier: Long? = null,
    val externalId: UUID? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNumber: String? = null,
    val active: Boolean = true,
    val confirmed: Boolean = false,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
