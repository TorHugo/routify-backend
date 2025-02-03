package com.dev.app.routify.domain.exception.template

import com.dev.app.routify.domain.exception.DomainExceptionHandler

class AccessDeniedException(message: String?) : DomainExceptionHandler(message)
