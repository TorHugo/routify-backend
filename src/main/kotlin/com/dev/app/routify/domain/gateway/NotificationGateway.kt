package com.dev.app.routify.domain.gateway

import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.enums.TypeNotificationEnum

interface NotificationGateway {
    fun save(domain: NotificationDomain)
    fun findByUserIdAndNotificationType(userId: Long, type: TypeNotificationEnum): NotificationDomain?
}
