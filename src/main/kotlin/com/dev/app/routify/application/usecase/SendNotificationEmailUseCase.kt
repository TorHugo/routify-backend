package com.dev.app.routify.application.usecase

import com.dev.app.routify.domain.entity.EmailDomain
import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.service.EmailService
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SendNotificationEmailUseCase(
    private val emailService: EmailService
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun execute(notification: NotificationDomain) {
        try {
            logger.info("c=SendNotificationUseCase m=execute() s=start email=${notification.identifier}")
            emailService.sendEmail(
                EmailDomain(
                    to = notification.toEmail,
                    subject = notification.subject,
                    body = notification.message
                )
            )
            logger.info("c=SendNotificationUseCase m=execute() s=done email=${notification.identifier}")
        } catch (ex: Exception) {
            logger.error("c=SendNotificationUseCase m=execute() s=error-generic email=${notification.identifier} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
