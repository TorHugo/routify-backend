package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.EmailTemplateDTO
import com.dev.app.routify.domain.entity.EmailTemplateDomain
import com.dev.app.routify.domain.objects.EmailTemplateKey

fun EmailTemplateDTO.toDomain(): EmailTemplateDomain {
    return EmailTemplateDomain(
        identifier = EmailTemplateKey(this.templateKey),
        subject = this.subject,
        body = this.body,
        isHtml = this.isHtml,
        version = this.version,
        parameters = this.parameters
    )
}

fun EmailTemplateDomain.toApplicationDTO(): EmailTemplateDTO {
    return EmailTemplateDTO(
        templateKey = this.identifier.value,
        subject = this.subject,
        body = this.body,
        isHtml = this.isHtml,
        version = this.version,
        parameters = this.parameters,
        active = this.active,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
