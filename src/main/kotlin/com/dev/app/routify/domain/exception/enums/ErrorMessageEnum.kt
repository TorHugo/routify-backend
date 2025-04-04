package com.dev.app.routify.domain.exception.enums

enum class ErrorMessageEnum(val message: String) {
    // generic error
    INTERNAL_SERVER_ERROR(message = "generic.error.internal"),

    // user error
    ERROR_USER_ALREADY_EXISTS(message = "user.already.exist"),
    ERROR_USER_NOT_FOUND(message = "user.not.found"),
    ERROR_EMAIL_IS_NOT_NULL(message = "email.cannot.be.null"),
    ERROR_EMAIL_IS_NOT_VALID(message = "email.cannot.is.not.valid"),
    ERROR_USER_IS_NOT_ACTIVE(message = "user.is.not.active"),
    ERROR_USER_IS_NOT_CONFIRMED(message = "user.is.not.confirmed"),

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

    // email template key error
    ERROR_EMAIL_TEMPLATE_NOT_FOUND(message = "email.template.key.not.found"),
    ERROR_EMAIL_TEMPLATE_ALREADY_EXISTS(message = "email.template.already.exist"),

    // event error
    ERROR_EVENT_IS_NOT_VALID(message = "event.is.not.valid"),

    // hashcode error
    ERROR_TOKEN_DIFFERENT_TO_SENT_USER(message = "token.different.to.sent.user"),
    ERROR_TOKEN_NOT_FOUND(message = "token.not.found"),
    ERROR_TOKEN_IS_EXPIRED(message = "token.is.expired"),
    ERROR_TOKEN_TYPE_NOT_FOUND(message = "token.type.not.found"),
    ERROR_TOKEN_ALREADY_USED(message = "token.already.used"),

    // authentication error
    ERROR_AUTHENTICATION_USER_IS_NOT_ACTIVE(message = "authentication.user.is.not.active"),
    ERROR_AUTHENTICATION_INVALID_ARGUMENTS(message = "authentication.invalid.arguments"),
    ERROR_AUTHENTICATION_SCOPES_NOT_FOUND(message = "authentication.scopes.not.found"),
    ERROR_AUTHENTICATION_ACCESS_DENIED(message = "authentication.access.denied"),
    ERROR_AUTHENTICATION_BEARER_TOKEN_IS_NOT_EMPTY(message = "authentication.bearer.token.is.not.empty"),

    // headers error
    ERROR_HEADERS_OBLIGATORY(message = "headers.is.obligatory"),

    // ScopeKeyEnum error
    ERROR_SCOPE_KEY_NOT_FOUND(message = "scope.key.not.found"),

    // JWT error
    ERROR_JWT_IS_EXPIRED(message = "jwt.is.expired"),

    // ForgotPassword error
    ERROR_FORGOT_PASSWORD_IS_EXPIRED(message = "forgot.password.is.expired"),
    ERROR_FORGOT_PASSWORD_TOKEN_HAS_BEEN_USED(message = "forgot.password.token.has.been.used"),


    // route error
    ERROR_ROUTE_STATUS_IS_NOT_VALID(message = "route.status.is.not.valid"),
    ERROR_ROUTE_USER_ALREADY_UNFINISHED_ROUTES(message = "route.user.unfinished.already.routes"),
}
