package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.SendingEmailDTO
import com.dev.app.routify.domain.entity.SendingEmailDomain

fun SendingEmailDomain.toApplicationDTO(): SendingEmailDTO {
    return SendingEmailDTO(
        to = this.to!!,
        subject = this.subject!!,
        body = this.body!!,
        isHtml = this.isHtml,
        attachments = this.attachments
    )
}
