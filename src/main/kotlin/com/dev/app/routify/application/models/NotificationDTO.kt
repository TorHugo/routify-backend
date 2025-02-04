package com.dev.app.routify.application.models

import java.time.LocalDateTime

data class NotificationDTO(
    val identifier: Long?,
    val userId: Long,
    val toEmail: String?,
    val status: String,
    val type: String,
    val subject: String,
    val message: String,
    val parameters: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
