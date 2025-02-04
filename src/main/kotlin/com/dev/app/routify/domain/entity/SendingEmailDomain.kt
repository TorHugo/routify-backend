package com.dev.app.routify.domain.entity

import java.io.File
import java.io.Serializable

data class SendingEmailDomain(
    val to: String? = null,
    val subject: String? = null,
    val body: String? = null,
    val isHtml: Boolean = true,
    val attachments: List<File> = emptyList()
) : Serializable
