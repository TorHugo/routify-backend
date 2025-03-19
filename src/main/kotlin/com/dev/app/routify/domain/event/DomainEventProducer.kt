package com.dev.app.routify.domain.event

import com.dev.app.routify.domain.enums.DomainEventTypeEnum

interface DomainEventProducer {
    fun publish(domainEventType: DomainEventTypeEnum, message: DomainEvent): Boolean
}
