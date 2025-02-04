package com.dev.app.routify.application.models

import java.io.File

data class SendingEmailDTO(
    val to: String,
    val subject: String,
    val body: String,
    val isHtml: Boolean,
    val attachments: List<File> = emptyList()
)
