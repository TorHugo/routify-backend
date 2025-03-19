package com.dev.app.routify.domain.gateway

import com.dev.app.routify.domain.entity.NotificationDomain

interface NotificationGateway {
    fun save(domain: NotificationDomain)
    fun delete(domain: NotificationDomain)
    fun findByUserIdAndNotificationType(userId: Long, type: String): NotificationDomain?
}
