package com.dev.app.routify.infrastructure.event.template

import com.amazonaws.services.sns.model.MessageAttributeValue
import com.dev.app.routify.domain.enums.DataTypeMessageEnum
import com.dev.app.routify.domain.enums.DomainEventTypeEnum
import com.dev.app.routify.domain.enums.MessageAttributeKeyEnum
import com.dev.app.routify.infrastructure.event.SNSEventProducerImpl
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Qualifier("NotificationEventTemplate")
class NotificationEventTemplate(
    override val gson: Gson,
    @Value("\${spring.aws.region}")
    override val region: String,
    @Value("\${spring.aws.sns.domain-events-topic}")
    override val snsTopic: String
) : SNSEventProducerImpl(
    gson,
    region,
    snsTopic
) {

    override fun getAttributes(): MutableMap<String, MessageAttributeValue> {
        val attributes = mutableMapOf<String, MessageAttributeValue>()
        val event = DomainEventTypeEnum.NOTIFICATION_EVENT.type
        attributes[MessageAttributeKeyEnum.TYPE.type] =
            MessageAttributeValue()
                .withStringValue(event)
                .withDataType(DataTypeMessageEnum.STRING.type)
        return attributes
    }
}
