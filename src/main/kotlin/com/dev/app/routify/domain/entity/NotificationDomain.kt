package com.dev.app.routify.domain.entity

import com.dev.app.routify.domain.objects.StatusNotification
import com.dev.app.routify.domain.objects.TypeNotification
import java.io.Serializable
import java.time.LocalDateTime

data class NotificationDomain(
    override val identifier: Long? = null,
    val userId: Long,
    val toEmail: String? = null,
    val status: StatusNotification,
    val type: TypeNotification,
    val subject: String? = null,
    val message: String? = null,
    val parameters: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null
) : Serializable, AggregateRoot<Long?>()
