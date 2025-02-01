package com.dev.app.routify.domain.objects

import com.dev.app.routify.domain.exception.template.DomainException

data class Email(val value: String) {
    init {
        validate(value)
    }

    companion object {
        private const val DEFAULT_EMAIL_IS_NOT_NULL: String = "email.cannot.be.null"
        private const val DEFAULT_EMAIL_IS_NOT_VALID: String = "email.cannot.is.not.valid"
        private const val DEFAULT_EMAIL_REGEX: String = "^(.+)@(.+)$"

        fun validate(currentValue: String) {
            if (currentValue.isEmpty()) {
                throw DomainException(DEFAULT_EMAIL_IS_NOT_NULL)
            }
            if (!currentValue.matches(Regex(DEFAULT_EMAIL_REGEX))) {
                throw DomainException(DEFAULT_EMAIL_IS_NOT_VALID)
            }
        }
    }
}
