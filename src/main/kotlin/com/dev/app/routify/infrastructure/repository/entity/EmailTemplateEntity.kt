package com.dev.app.routify.infrastructure.repository.entity

import com.dev.app.routify.infrastructure.repository.helpers.EmailTemplateKeyHelper
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "email_template_tb", schema = "routify_db")
open class EmailTemplateEntity(
    @EmbeddedId
    open var emailTemplateKeys: EmailTemplateKeyHelper,
    open var subject: String? = null,
    open var body: String? = null,
    open var isHtml: Boolean? = true,

    open var parameters: String? = null,

    open var createdAt: LocalDateTime? = null,
    open var updatedAt: LocalDateTime? = null
)
