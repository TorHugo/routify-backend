package com.dev.app.routify.domain.exception.enums

enum class ErrorMessageEnum(val message: String) {
    // generic error
    INTERNAL_SERVER_ERROR(message = "generic.error.internal"),
    ERROR_GENERIC(message = "generic.error.message"),

    // user error
    ERROR_USER_ALREADY_EXISTS(message = "user.already.exist"),
    ERROR_USER_NOT_FOUND(message = "user.not.found"),
    ERROR_EMAIL_IS_NOT_NULL(message = "email.cannot.be.null"),
    ERROR_EMAIL_IS_NOT_VALID(message = "email.cannot.is.not.valid"),

    // password error
    ERROR_PASSWORD_CANNOT_BE_NULL(message = "password.cannot.be.null"),
    ERROR_PASSWORD_LEAST_EIGHT_CHARACTERS(message = "password.least.eight.characters"),
    ERROR_PASSWORD_DOES_NOT_CONTAIN_UPPERCASE(message = "password.does.not.contain.uppercase"),
    ERROR_PASSWORD_DOES_NOT_CONTAIN_LOWERCASE(message = "password.does.not.contain.lowercase"),
    ERROR_PASSWORD_DOES_NOT_CONTAIN_SPECIAL_CHARACTER(message = "password.does.not.contain.special.character"),

    // notification error
    ERROR_NOTIFICATION_STATUS_IS_NOT_VALID(message = "notification.status.is.not.valid"),
    ERROR_NOTIFICATION_TYPE_IS_NOT_VALID(message = "notification.type.is.not.valid"),
    ERROR_NOTIFICATION_PARAMETERS_IS_NOT_NULL(message = "notification.parameters.cant.be.null"),
    ERROR_NOTIFICATION_EMAIL_NOT_FOUND(message = "notification.not.found"),
    ERROR_NOTIFICATION_STATUS_IS_NOT_PENDING(message = "notification.status.not.pending"),

    // hashcode error
    ERROR_HASHCODE_DIFFERENT_TO_SENT_USER(message = "hashcode.different.to.sent.user"),
    ERROR_HASHCODE_IS_EXPIRED(message = "hashcode.is.expired")
}
