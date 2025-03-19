package com.dev.app.routify.infrastructure.event

import com.dev.app.routify.domain.enums.DomainEventTypeEnum
import com.dev.app.routify.domain.event.DomainEvent
import com.dev.app.routify.domain.event.DomainEventProducer
import com.dev.app.routify.infrastructure.event.template.AttributeScopesEventTemplate
import com.dev.app.routify.infrastructure.event.template.NotificationEventTemplate
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DomainEventProducerImpl(
    private val gson: Gson,
    private val notificationEventTemplate: NotificationEventTemplate,
    private val attributeScopesEventTemplate: AttributeScopesEventTemplate
) : DomainEventProducer {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * Based on the domain event, it calls the respective producer to apply the filter policy for SQS consumers.
     *
     * @param message The message object to be published. It can be of any type and will be
     *                converted to JSON before sending.
     * @return Boolean indicating whether the message was successfully published (true) or not (false).
     *         Returns false if an exception occurs during the publishing process.
     */
    override fun publish(domainEventType: DomainEventTypeEnum, message: DomainEvent): Boolean {
        logger.info("c=EventProducer m=publishMessage s=Start domainEventType=$domainEventType")
        message.domain = gson.toJson(message.domain)
        message.message = gson.toJson(message.message)
        val isPublished = when (domainEventType) {
            DomainEventTypeEnum.NOTIFICATION_EVENT ->
                notificationEventTemplate.publishMessage(message = message)
            DomainEventTypeEnum.ATTRIBUTE_SCOPE_EVENT ->
                attributeScopesEventTemplate.publishMessage(message = message)
        }
        logger.info("c=EventProducer m=publishMessage s=Done domainEventType=$domainEventType isPublished=$isPublished")
        return isPublished
    }
}
