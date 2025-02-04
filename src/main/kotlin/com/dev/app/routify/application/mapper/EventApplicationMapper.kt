package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.EventDTO
import com.dev.app.routify.domain.entity.EventDomain

fun EventDTO.toDomain(): EventDomain {
    return EventDomain(
        eventType = this.eventType,
        domain = this.domain,
        parameters = this.parameters
    )
}
