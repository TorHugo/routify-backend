package com.dev.app.routify.domain.objects

import com.dev.app.routify.domain.enums.StatusRouteEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException

data class StatusRoute(val value: String) {
    init {
        validate(value)
    }

    companion object {
        fun validate(value: String) {
            if (!StatusRouteEnum.isExistsValue(value)) {
                throw DomainException(ErrorMessageEnum.ERROR_ROUTE_STATUS_IS_NOT_VALID.message)
            }
        }
    }
}
