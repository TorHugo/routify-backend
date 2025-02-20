package com.dev.app.routify.infrastructure.event

import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.extension.replaceAllParameters
import com.dev.app.routify.domain.objects.StatusNotification
import com.dev.app.routify.domain.objects.TypeNotification
import com.dev.app.routify.domain.service.NotificationEmailService
import com.dev.app.routify.infrastructure.event.models.SendBaseNotificationEventDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class SendBaseNotificationEvent(
    private val notificationEmailService: NotificationEmailService
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    fun sendEmail(dto: SendBaseNotificationEventDTO){
        logger.info("c=SendNotificationWithHashTokenListener m=handleSendNotificationWithHashTokenListener() s=start email=${dto.user.email} type=${dto.typeNotification.value}")
        val parameters = dto.parameters
        val mailTemplate = dto.template
        val subject = mailTemplate.subject
        val body = mailTemplate.message.replaceAllParameters(parameters)

        val notificationDomain = NotificationDomain(
            userId = dto.user.identifier!!,
            toEmail = dto.user.email,
            status = StatusNotification(dto.statusNotification.value),
            type = TypeNotification(dto.typeNotification.value),
            subject = subject,
            message = body
        )

        notificationEmailService.execute(
            domain = notificationDomain
        )
        logger.info("c=SendNotificationWithHashTokenListener m=handleSendNotificationWithHashTokenListener() s=done email=${dto.user.email} type=${dto.typeNotification.value}")
    }
}