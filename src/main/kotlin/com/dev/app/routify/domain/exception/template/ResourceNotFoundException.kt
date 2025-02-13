package com.dev.app.routify.domain.exception.template

import com.dev.app.routify.domain.exception.DomainExceptionHandler

class ResourceNotFoundException(message: String?) : DomainExceptionHandler(message)
