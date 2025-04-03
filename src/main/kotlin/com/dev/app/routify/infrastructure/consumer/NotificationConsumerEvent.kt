package com.dev.app.routify.infrastructure.consumer

import com.dev.app.routify.domain.event.DomainEvent
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.factory.CommunicationFactory
import com.dev.app.routify.infrastructure.consumer.models.EventConsumerDTO
import com.google.gson.Gson
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NotificationConsumerEvent(
    private val gson: Gson,
    private val communicationFactory: CommunicationFactory
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @SqsListener("RoutifySendNotificationQueue")
    fun subscribe(message: EventConsumerDTO) {
        logger.info("c=NotificationConsumerEvent m=subscribe s=Start messageId=${message.messageId} type=${message.type}")
        val event = gson.fromJson(message.message, DomainEvent::class.java)
        if (event.subType == null){
            throw DomainException("event.sub.type.is.mandatory.and.not.null")
        }
        communicationFactory.getInstance(
            subTypeEvent = event.subType
        ).publish(event = event)
        logger.info("c=NotificationConsumerEvent m=subscribe s=Done messageId=${message.messageId} type=${message.type}")
    }
}
