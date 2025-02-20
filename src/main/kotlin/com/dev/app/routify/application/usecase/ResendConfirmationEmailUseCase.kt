package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.models.EventDTO
import com.dev.app.routify.domain.enums.EventTypeEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserGateway
import com.dev.app.routify.domain.objects.Parameter
import com.dev.app.routify.domain.service.EventService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class ResendConfirmationEmailUseCase(
    private val eventService: EventService,
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_KEY_FULL_NAME: String = "name"
        private val EVENT_SEND_CONFIRMATION: EventTypeEnum = EventTypeEnum.EVENT_SEND_CONFIRMATION_EMAIL_TO_USER
    }

    fun execute(email: String) {
        try {
            logger.info("c=ResendConfirmationEmailUseCase m=execute() s=start email=$email")
            val user = userGateway.findByEmail(
                email = email
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)

            eventService.publish(
                EventDTO(
                    eventType = EVENT_SEND_CONFIRMATION,
                    domain = user,
                    parameters = listOf(
                        Parameter(
                            key = DEFAULT_KEY_FULL_NAME,
                            value = user.fullName()
                        )
                    )
                )
            )
        }  catch (ex: Exception) {
            logger.error("c=ResendConfirmationEmailUseCase m=execute() s=generic-error email=$email message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}