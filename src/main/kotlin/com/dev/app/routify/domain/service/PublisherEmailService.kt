package com.dev.app.routify.domain.service

import com.dev.app.routify.domain.entity.NotificationDomain

interface PublisherEmailService {
    fun publish(domain: NotificationDomain)
}
