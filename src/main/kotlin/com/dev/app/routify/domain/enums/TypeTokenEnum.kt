package com.dev.app.routify.domain.enums

enum class TypeTokenEnum(val value: String) {
    TOKEN_CONFIRMATION_ACCOUNT(value = "TOKEN_CONFIRMATION_ACCOUNT"),
    TOKEN_FORGOT_PASSWORD(value = "TOKEN_FORGOT_PASSWORD");

    companion object {
        fun isExistsValue(
            value: String
        ): Boolean {
            return entries.toTypedArray().any { it.value == value }
        }
    }
}
