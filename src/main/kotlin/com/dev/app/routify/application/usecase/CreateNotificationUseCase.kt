package com.dev.app.routify.application.usecase

import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.NotificationGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CreateNotificationUseCase(
    private val notificationGateway: NotificationGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun execute(domain: NotificationDomain) {
        try {
            notificationGateway.save(domain)
        } catch (ex: Exception) {
            logger.error("c=CreateNotificationUseCase m=execute() s=error-generic identifier=${domain.identifier} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
