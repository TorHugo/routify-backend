package com.dev.app.routify.infrastructure.consumer.models

import com.fasterxml.jackson.annotation.JsonProperty

data class EventConsumerDTO(
    @JsonProperty("Type") val type: String,
    @JsonProperty("MessageId") val messageId: String,
    @JsonProperty("TopicArn") val topicArn: String,
    @JsonProperty("Message") val message: String,
    @JsonProperty("Timestamp") val timestamp: String,
    @JsonProperty("SignatureVersion") val signatureVersion: String,
    @JsonProperty("Signature") val signature: String,
    @JsonProperty("SigningCertURL") val signingCertUrl: String,
    @JsonProperty("UnsubscribeURL") val unsubscribeUrl: String,
    @JsonProperty("MessageAttributes") val messageAttributes: Map<String, MessageAttribute>
)

data class MessageAttribute(
    @JsonProperty("Type") val type: String,
    @JsonProperty("Value") val value: String
)
