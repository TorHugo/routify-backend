package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.NotificationDTO
import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.objects.StatusNotification
import com.dev.app.routify.domain.objects.TypeNotification

fun NotificationDTO.toDomain(): NotificationDomain {
    return NotificationDomain(
        identifier = this.identifier,
        userId = this.userId,
        status = StatusNotification(this.status),
        type = TypeNotification(this.type),
        subject = this.subject,
        message = this.message,
        parameters = this.parameters,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun NotificationDomain.toApplicationDTO(): NotificationDTO {
    return NotificationDTO(
        identifier = this.identifier,
        userId = this.userId,
        toEmail = this.toEmail,
        status = this.status.value,
        type = this.type.value,
        subject = this.subject!!,
        message = this.message!!,
        parameters = this.parameters,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
