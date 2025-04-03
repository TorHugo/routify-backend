package com.dev.app.routify.domain.service

import com.dev.app.routify.application.models.PublishingEmailDTO

interface MailService {
    fun publishEmail(dto: PublishingEmailDTO)
}
