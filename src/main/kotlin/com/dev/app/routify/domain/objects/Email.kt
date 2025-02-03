package com.dev.app.routify.domain.objects

import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException

data class Email(val value: String) {
    init {
        validate(value)
    }

    companion object {
        private const val DEFAULT_EMAIL_REGEX: String = "^(.+)@(.+)$"

        fun validate(currentValue: String) {
            if (currentValue.isEmpty()) {
                throw DomainException(ErrorMessageEnum.ERROR_EMAIL_IS_NOT_NULL.message)
            }
            if (!currentValue.matches(Regex(DEFAULT_EMAIL_REGEX))) {
                throw DomainException(ErrorMessageEnum.ERROR_EMAIL_IS_NOT_VALID.message)
            }
        }
    }
}
