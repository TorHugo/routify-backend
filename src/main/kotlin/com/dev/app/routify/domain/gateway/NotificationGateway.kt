package com.dev.app.routify.domain.gateway

import com.dev.app.routify.domain.entity.NotificationDomain

interface NotificationGateway {
    fun save(domain: NotificationDomain)
}
