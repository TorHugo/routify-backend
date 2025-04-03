package com.dev.app.routify.domain.enums

enum class TypeTokenEnum(val value: String) {
    TOKEN_CONFIRMATION_ACCOUNT(value = "TOKEN_CONFIRMATION_ACCOUNT"),
    TOKEN_RESET_CUSTOMER_PASSWORD(value = "TOKEN_RESET_CUSTOMER_PASSWORD");

    companion object {
        fun isExistsValue(
            value: String
        ): Boolean {
            return entries.toTypedArray().any { it.value == value }
        }
    }
}
