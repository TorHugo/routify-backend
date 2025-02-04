package com.dev.app.routify.domain.service

import com.dev.app.routify.application.models.EventDTO

interface EventService {
    fun publish(dto: EventDTO)
}
