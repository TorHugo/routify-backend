package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.usecase.CreateNotificationUseCase
import com.dev.app.routify.application.usecase.SendNotificationEmailUseCase
import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.service.PublisherEmailService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PublisherEmailServiceImpl(
    private val sendNotificationEmailUseCase: SendNotificationEmailUseCase,
    private val createNotificationUseCase: CreateNotificationUseCase
) : PublisherEmailService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun publish(domain: NotificationDomain) {
        try {
            logger.info("c=ConfirmationCreatingAccountListener m=publish() s=start email=${domain.toEmail}")
            sendNotificationEmailUseCase.execute(
                domain.toApplicationDTO()
            )

            createNotificationUseCase.execute(
                domain.toApplicationDTO()
            )
            logger.info("c=ConfirmationCreatingAccountListener m=publish() s=done email=${domain.toEmail}")
        } catch (ex: Exception) {
            logger.info("c=ConfirmationCreatingAccountListener m=publish() s=error-generic email=${domain.toEmail} message=${ex.message}")
            throw GenericException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
