package com.dev.app.routify.domain.enums

enum class TypeNotificationEnum(val value: String) {
    SEND_CONFIRMATION_ACCOUNT(value = "SEND_CONFIRMATION_ACCOUNT"),
    SEND_FORGOT_PASSWORD(value = "SEND_FORGOT_PASSWORD"),
    SEND_WELCOME_EMAIL(value = "SEND_WELCOME_USER");

    companion object {
        fun isExistsValue(
            value: String
        ): Boolean {
            return entries.toTypedArray().any { it.value == value }
        }
    }
}
