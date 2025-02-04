package com.dev.app.routify.application.models

import com.dev.app.routify.domain.enums.EventTypeEnum
import com.dev.app.routify.domain.objects.Parameter

data class EventDTO(
    val identifier: String? = null,
    val eventType: EventTypeEnum,
    val domain: Any,
    val parameters: List<Parameter>
)
