package com.dev.app.routify.domain.enums

enum class DomainEventTypeEnum(val type: String) {
    NOTIFICATION_EVENT(type = "notification_event"),
    CUSTOMER_PERMISSIONS_EVENT(type = "customer_permissions_event")
}
