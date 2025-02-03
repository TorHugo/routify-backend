package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.domain.entity.EventDomain
import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.enums.EventTypeEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.service.EventService
import com.dev.app.routify.infrastructure.event.models.ConfirmationCreatingAccountEventDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class EventServiceImpl(
    private val eventPublisher: ApplicationEventPublisher
) : EventService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun publish(entryEvent: EventDomain) {
        try {
            logger.info("c=EventService m=publish() s=start identifier=${entryEvent.identifier} eventType=${entryEvent.eventType}")
            when (entryEvent.eventType) {
                EventTypeEnum.EVENT_SEND_CONFIRMATION_CREATING_ACCOUNT -> {
                    val user = entryEvent.domain as UserDomain
                    val event = ConfirmationCreatingAccountEventDTO(
                        transaction = entryEvent.identifier.value,
                        email = user.email.value,
                        parameters = entryEvent.parameters
                    )
                    eventPublisher.publishEvent(event)
                }
            }
            logger.info("c=EventService m=publish() s=done identifier=${entryEvent.identifier} eventType=${entryEvent.eventType}")
        } catch (e: Exception) {
            logger.error("c=EventService m=publish() s=error identifier=${entryEvent.identifier} eventType=${entryEvent.eventType} message=${e.message}")
            throw GenericException(ErrorMessageEnum.ERROR_GENERIC.message)
        }
    }
}
