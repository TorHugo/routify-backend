package com.dev.app.routify.domain.objects

import com.dev.app.routify.domain.enums.EmailTemplateKeyEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException

data class EmailTemplateKey(val value: String) {
    init {
        validate(value)
    }

    companion object {
        fun validate(templateKey: String) {
            if (EmailTemplateKeyEnum.getEmailTemplate(templateKey) == null) {
                throw DomainException(ErrorMessageEnum.ERROR_EMAIL_TEMPLATE_NOT_FOUND.message)
            }
        }
    }
}
