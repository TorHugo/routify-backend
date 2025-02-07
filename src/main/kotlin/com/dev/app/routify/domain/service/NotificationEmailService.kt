package com.dev.app.routify.domain.service

import com.dev.app.routify.domain.entity.NotificationDomain

interface NotificationEmailService {
    fun execute(domain: NotificationDomain)
}
