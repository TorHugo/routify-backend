package com.dev.app.routify.domain.enums

enum class TypeNotificationEnum(val type: String) {
    SEND_CONFIRMATION_ACCOUNT(type = "SEND_CONFIRMATION_ACCOUNT") ;

    companion object {
        fun isExistsValue(
            value: String
        ): Boolean {
            return entries.toTypedArray().any { it.type == value }
        }
    }
}
