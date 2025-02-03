package com.dev.app.routify.domain.objects

import com.dev.app.routify.domain.enums.StatusNotificationEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException

data class StatusNotification(val value: String) {
    init {
        validate(value)
    }

    companion object {
        fun validate(value: String) {
            if (!StatusNotificationEnum.isExistsValue(value)) {
                throw DomainException(ErrorMessageEnum.ERROR_NOTIFICATION_STATUS_IS_NOT_VALID.message)
            }
        }
    }
}
