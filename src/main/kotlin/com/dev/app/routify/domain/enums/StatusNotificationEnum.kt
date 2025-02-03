package com.dev.app.routify.domain.enums

enum class StatusNotificationEnum(
    val value: String
) {
    SENDING(value = "SENDING"),
    PENDING(value = "PENDING"),
    CONFIRMED(value = "CONFIRMED"),
    EXPIRED(value = "EXPIRED"),
    CANCELED(value = "CANCELED");

    companion object {
        fun isExistsValue(
            value: String
        ): Boolean {
            return entries.toTypedArray().any { it.value == value }
        }
    }
}
