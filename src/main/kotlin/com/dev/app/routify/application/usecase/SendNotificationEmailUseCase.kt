package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.models.NotificationDTO
import com.dev.app.routify.domain.entity.PublishingEmailDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.service.MailService
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SendNotificationEmailUseCase(
    private val mailService: MailService
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun execute(dto: NotificationDTO) {
        try {
            logger.info("c=SendNotificationUseCase m=execute() s=start email=${dto.identifier}")
            val domain = PublishingEmailDomain(
                to = dto.toEmail,
                subject = dto.subject,
                body = dto.message
            )
            mailService.publishEmail(
                dto = domain.toApplicationDTO()
            )
            logger.info("c=SendNotificationUseCase m=execute() s=done email=${dto.identifier}")
        } catch (ex: Exception) {
            logger.error("c=SendNotificationUseCase m=execute() s=error-generic email=${dto.identifier} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
