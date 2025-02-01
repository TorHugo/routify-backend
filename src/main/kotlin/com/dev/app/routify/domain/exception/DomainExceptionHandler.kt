package com.dev.notification.backend.api.domain.exception

abstract class DomainExceptionHandler : RuntimeException {
    constructor(message: String, parameter: String) : super("$message | $parameter")
    constructor(message: String) : super(message)

    override fun getLocalizedMessage(): String {
        return super.message ?: "Unknown message"
    }
}
