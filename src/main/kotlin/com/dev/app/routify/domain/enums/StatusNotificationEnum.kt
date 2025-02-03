package com.dev.app.routify.domain.enums

enum class StatusNotificationEnum(
    val type: String
) {
    SENDING(type = "SENDING"),
    PENDING(type = "PENDING"),
    CONFIRMED(type = "CONFIRMED"),
    EXPIRED(type = "EXPIRED"),
    CANCELED(type = "CANCELED");

    companion object {
        fun isExistsValue(
            value: String
        ): Boolean {
            return entries.toTypedArray().any { it.type == value }
        }
    }
}
