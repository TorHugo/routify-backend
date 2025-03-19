package com.dev.app.routify.domain.enums

enum class TypeNotificationEnum(val value: String) {
    SEND_CUSTOMER_CONFIRMATION(value = "SEND_CUSTOMER_CONFIRMATION"),
    SEND_CUSTOMER_RESET_PASSWORD(value = "SEND_CUSTOMER_RESET_PASSWORD"),
    SEND_WELCOME_EMAIL(value = "SEND_WELCOME_USER");

    companion object {
        fun isExistsValue(
            value: String
        ): Boolean {
            return entries.toTypedArray().any { it.value == value }
        }
    }
}
