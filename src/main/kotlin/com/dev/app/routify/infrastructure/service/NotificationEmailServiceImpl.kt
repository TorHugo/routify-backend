package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.usecase.CreateNotificationUseCase
import com.dev.app.routify.application.usecase.SendNotificationEmailUseCase
import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.service.NotificationEmailService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NotificationEmailServiceImpl(
    private val notificationUseCase: SendNotificationEmailUseCase,
    private val createNotificationUseCase: CreateNotificationUseCase
) : NotificationEmailService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(domain: NotificationDomain) {
        try {
            logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=start email=${domain.toEmail}")
            notificationUseCase.execute(
                domain.toApplicationDTO()
            )

            createNotificationUseCase.execute(
                domain.toApplicationDTO()
            )
            logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=done email=${domain.toEmail}")
        } catch (ex: Exception) {
            logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=error-generic email=${domain.toEmail} message=${ex.message}")
            throw GenericException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
