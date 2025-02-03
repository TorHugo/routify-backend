package com.dev.app.routify.domain.service

import com.dev.app.routify.domain.entity.EventDomain

interface EventService {
    fun publish(entryEvent: EventDomain)
}
