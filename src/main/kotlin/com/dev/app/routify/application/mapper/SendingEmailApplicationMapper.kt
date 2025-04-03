package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.PublishingEmailDTO
import com.dev.app.routify.domain.entity.PublishingEmailDomain

fun PublishingEmailDomain.toApplicationDTO(): PublishingEmailDTO {
    return PublishingEmailDTO(
        to = this.to!!,
        subject = this.subject!!,
        body = this.body!!,
        isHtml = this.isHtml,
        attachments = this.attachments
    )
}
