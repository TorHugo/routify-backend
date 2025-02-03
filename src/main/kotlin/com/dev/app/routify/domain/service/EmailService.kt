package com.dev.app.routify.domain.service

import com.dev.app.routify.domain.entity.EmailDomain

interface EmailService {
    fun sendEmail(domain: EmailDomain)
}
