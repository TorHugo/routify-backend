package com.dev.app.routify.domain.service

import com.dev.app.routify.application.models.SendingEmailDTO

interface EmailService {
    fun sendEmail(dto: SendingEmailDTO)
}
