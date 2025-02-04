package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.models.EventDTO
import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.enums.EventTypeEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.service.EventService
import com.dev.app.routify.infrastructure.event.models.ConfirmationCreatingAccountEventDTO
import com.dev.app.routify.infrastructure.event.models.UserScopesEventDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class EventServiceImpl(
    private val eventPublisher: ApplicationEventPublisher
) : EventService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_EMPTY_SCOPE: String = ""
        private const val DEFAULT_SCOPE_KEY: String = "scope-user"
    }

    override fun publish(dto: EventDTO) {
        val entryEvent = dto.toDomain()
        try {
            logger.info("c=EventService m=publish() s=start identifier=${entryEvent.identifier} eventType=${entryEvent.eventType}")

            when (entryEvent.eventType) {
                EventTypeEnum.EVENT_SEND_CONFIRMATION_CREATING_ACCOUNT -> {
                    val user = entryEvent.domain as UserDomain
                    val event = ConfirmationCreatingAccountEventDTO(
                        transaction = entryEvent.identifier.value,
                        user = user,
                        parameters = entryEvent.parameters
                    )
                    eventPublisher.publishEvent(event)
                }

                EventTypeEnum.EVENT_USER_SCOPES -> {
                    val user = entryEvent.domain as UserDomain
                    val scopes = entryEvent.parameters?.find { it.key == DEFAULT_SCOPE_KEY }?.value ?: DEFAULT_EMPTY_SCOPE

                    val event = UserScopesEventDTO(
                        transaction = entryEvent.identifier.value,
                        userId = user.identifier!!,
                        scopeKey = scopes
                    )
                    eventPublisher.publishEvent(event)
                }
            }
            logger.info("c=EventService m=publish() s=done identifier=${entryEvent.identifier} eventType=${entryEvent.eventType}")
        } catch (e: Exception) {
            logger.error("c=EventService m=publish() s=error identifier=${entryEvent.identifier} eventType=${entryEvent.eventType} message=${e.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
