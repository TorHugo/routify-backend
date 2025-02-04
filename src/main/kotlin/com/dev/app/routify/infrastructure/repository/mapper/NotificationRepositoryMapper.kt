package com.dev.app.routify.infrastructure.repository.mapper

import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.objects.StatusNotification
import com.dev.app.routify.domain.objects.TypeNotification
import com.dev.app.routify.infrastructure.repository.entity.NotificationEntity

fun NotificationDomain.toEntity(): NotificationEntity {
    return NotificationEntity(
        notificationId = this.identifier,
        userId = this.userId,
        type = this.type.value,
        status = this.status.value,
        createdAt = this.createdAt
    )
}

fun NotificationEntity.toDomain(): NotificationDomain {
    return NotificationDomain(
        identifier = this.notificationId,
        userId = this.userId,
        status = StatusNotification(this.status!!),
        type = TypeNotification(this.type!!),
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt
    )
}
