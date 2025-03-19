package com.dev.app.routify.infrastructure.repository.mapper

import com.dev.app.routify.domain.entity.EmailTemplateDomain
import com.dev.app.routify.domain.objects.EmailTemplateKey
import com.dev.app.routify.infrastructure.repository.entity.EmailTemplateEntity
import com.dev.app.routify.infrastructure.repository.helpers.EmailTemplateKeyHelper

fun EmailTemplateEntity.toDomain(): EmailTemplateDomain {
    return EmailTemplateDomain(
        identifier = EmailTemplateKey(this.emailTemplateKeys.templateKey),
        subject = this.subject!!,
        body = this.body!!,
        isHtml = this.isHtml!!,
        version = this.emailTemplateKeys.version,
        parameters = this.parameters!!,
        active = this.emailTemplateKeys.active,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun EmailTemplateDomain.toEntity(): EmailTemplateEntity {
    return EmailTemplateEntity(
        emailTemplateKeys = EmailTemplateKeyHelper(
            templateKey = this.identifier.value,
            version = this.version,
            active = this.active
        ),
        subject = this.subject,
        body = this.body,
        isHtml = this.isHtml,
        parameters = this.parameters,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
