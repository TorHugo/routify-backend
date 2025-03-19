package com.dev.app.routify.infrastructure.event

import com.amazonaws.services.sns.AmazonSNSClientBuilder
import com.amazonaws.services.sns.model.MessageAttributeValue
import com.amazonaws.services.sns.model.PublishRequest
import com.dev.app.routify.domain.event.SNSEventProducer
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
abstract class SNSEventProducerImpl(
    val gson: Gson,
    @Value("\${spring.aws.region}")
    val region: String,
    @Value("\${spring.aws.sns.domain-events-topic}")
    val snsTopic: String
) : SNSEventProducer {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * This function is responsible for publishing a message to an AWS SNS topic.
     *
     * @param message The message to be published. It can be of any type, but it will be converted to JSON using the Gson library.
     *
     * @return A boolean indicating whether the message was successfully published.
     * - `true`: The message was published successfully.
     * - `false`: An error occurred while publishing the message.
     *
     * @throws Exception, An error occurred while publishing the message and logged.
     */
    override fun publishMessage(message: Any): Boolean {
        try {
            logger.info("c=SNSEventProducerImpl m=publishMessage s=Start")
            val sns = AmazonSNSClientBuilder
                .standard()
                .withRegion(region)
                .build()

            val publishMessage = PublishRequest()
                .withTopicArn(snsTopic)
                .withMessage(gson.toJson(message))

            for ((key, value) in getAttributes()) {
                publishMessage.addMessageAttributesEntry(key, value)
            }

            sns.publish(publishMessage)
            logger.info("c=SNSEventProducerImpl m=publishMessage s=Done")
            return true
        } catch (exception: Exception) {
            logger.error("c=SNSEventProducerImpl m=publishMessage s=Error message=${exception.message}")
            return false
        }
    }

    /**
     * The idea is that each implementation should add its specific attributes to the superclass, making it as general as possible.
     * **/
    abstract fun getAttributes(): MutableMap<String, MessageAttributeValue>
}
