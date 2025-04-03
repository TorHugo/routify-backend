package com.dev.app.routify.domain.service

import com.dev.app.routify.domain.event.DomainEvent

interface CommunicationService {
    fun publish(event: DomainEvent)
}
