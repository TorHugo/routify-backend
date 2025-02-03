package com.dev.app.routify.domain.objects

import com.dev.app.routify.domain.enums.TypeNotificationEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException

data class TypeNotification(val value: String) {
    init {
        validate(value)
    }

    companion object {
        fun validate(value: String) {
            if (!TypeNotificationEnum.isExistsValue(value)) {
                throw DomainException(ErrorMessageEnum.ERROR_NOTIFICATION_TYPE_IS_NOT_VALID.message)
            }
        }
    }
}
