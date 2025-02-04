package com.dev.app.routify.domain.objects

import com.dev.app.routify.domain.enums.TypeTokenEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.InternalServerException

data class TypeToken(val value: String) {
    init {
        validate(value)
    }

    companion object {
        fun validate(value: String) {
            if (!TypeTokenEnum.isExistsValue(value)) {
                throw InternalServerException(ErrorMessageEnum.ERROR_TOKEN_TYPE_NOT_FOUND.message)
            }
        }
    }
}
