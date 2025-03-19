package com.dev.app.routify.domain.enums

enum class DomainEventTypeEnum(val type: String) {
    NOTIFICATION_EVENT(type = "notification_event"),
    ATTRIBUTE_SCOPE_EVENT(type = "attribute_scope_event")
}
