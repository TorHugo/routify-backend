package com.dev.app.routify.domain.service

import com.dev.app.routify.domain.entity.SendingEmailDomain

interface EmailService {
    fun sendEmail(domain: SendingEmailDomain)
}
