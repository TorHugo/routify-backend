package com.dev.app.routify.domain.enums

import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.InternalServerException

enum class ScopeKeyEnum(val value: String) {
    DEFAULT_USER_SCOPE(value = "DEFAULT_USER_SCOPE"),
    DEFAULT_AUTH_SCOPE(value = "DEFAULT_AUTH_SCOPE"),
    ADMIN_TEMPLATE_SCOPE(value = "ADMIN_TEMPLATE_SCOPE");

    companion object {
        fun findValue(value: String): ScopeKeyEnum {
            return entries.find { it.value == value } ?: throw InternalServerException(ErrorMessageEnum.ERROR_SCOPE_KEY_NOT_FOUND.message)
        }
    }
}
