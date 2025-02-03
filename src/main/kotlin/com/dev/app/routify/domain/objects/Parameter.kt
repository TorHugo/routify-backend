package com.dev.app.routify.domain.objects

import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException

data class Parameter(
    val key: String,
    val value: String
) {
    init {
        validate(key, value)
    }

    companion object {
        fun validate(parameterName: String, parameterValue: String) {
            if (parameterName.isEmpty() || parameterValue.isEmpty()) {
                throw DomainException(ErrorMessageEnum.ERROR_NOTIFICATION_PARAMETERS_IS_NOT_NULL.message)
            }
        }
    }
}
