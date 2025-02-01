package com.dev.app.routify.infrastructure.exception.models

data class ExceptionData(
    val error: String,
    val message: String,
    val path: String
)
