package com.dev.app.routify.domain.event

interface SNSEventProducer {
    fun publishMessage(message: Any): Boolean
}
