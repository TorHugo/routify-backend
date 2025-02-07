package com.dev.app.routify.domain.enums

enum class TypeNotificationEnum(val value: String) {
    SEND_CONFIRMATION_ACCOUNT(value = "SEND_CONFIRMATION_ACCOUNT"),
    SEND_FORGOT_PASSWORD(value = "SEND_FORGOT_PASSWORD");

    companion object {
        fun isExistsValue(
            value: String
        ): Boolean {
            return entries.toTypedArray().any { it.value == value }
        }
    }
}
