package com.dev.app.routify.domain.entity

import com.dev.app.routify.domain.objects.EmailTemplateKey
import java.io.Serializable
import java.time.LocalDateTime

class EmailTemplateDomain(
    override val identifier: EmailTemplateKey,
    val subject: String,
    val body: String,
    val isHtml: Boolean = true,
    val version: String,
    val parameters: String,
    val active: Boolean = true,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
) : Serializable, AggregateRoot<EmailTemplateKey>()
