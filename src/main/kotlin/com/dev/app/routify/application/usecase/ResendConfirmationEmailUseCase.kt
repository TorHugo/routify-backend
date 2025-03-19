package com.dev.app.routify.application.usecase

import com.dev.app.routify.domain.enums.DomainEventTypeEnum
import com.dev.app.routify.domain.enums.SubTypeEventEnum
import com.dev.app.routify.domain.event.DomainEvent
import com.dev.app.routify.domain.event.DomainEventProducer
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ResendConfirmationEmailUseCase(
    private val domainEventProducer: DomainEventProducer,
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    fun execute(email: String) {
        try {
            logger.info("c=ResendConfirmationEmailUseCase m=execute() s=start email=$email")
            val user = userGateway.findByEmail(
                email = email
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)

            domainEventProducer.publish(
                domainEventType = DomainEventTypeEnum.NOTIFICATION_EVENT,
                message = DomainEvent(
                    domain = user,
                    subType = SubTypeEventEnum.SUB_TYPE_CONFIRMATION_CUSTOMER
                )
            )
        } catch (ex: Exception) {
            logger.error("c=ResendConfirmationEmailUseCase m=execute() s=generic-error email=$email message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
