package com.dev.app.routify.application.models

import java.time.LocalDateTime

data class EmailTemplateDTO(
    val templateKey: String,
    val subject: String,
    val body: String,
    val isHtml: Boolean,
    val version: String,
    val parameters: String,
    val active: Boolean,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
