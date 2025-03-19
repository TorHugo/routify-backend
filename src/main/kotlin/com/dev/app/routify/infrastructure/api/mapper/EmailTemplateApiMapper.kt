package com.dev.app.routify.infrastructure.api.mapper

import com.dev.app.routify.application.models.EmailTemplateDTO
import com.dev.app.routify.infrastructure.api.models.request.EmailTemplateRequestDTO

fun EmailTemplateRequestDTO.toApplicationDTO(): EmailTemplateDTO {
    return EmailTemplateDTO(
        templateKey = this.templateKey,
        subject = this.subject,
        body = this.body,
        isHtml = this.isHtml,
        version = this.version,
        parameters = this.parameters,
        active = this.active
    )
}
