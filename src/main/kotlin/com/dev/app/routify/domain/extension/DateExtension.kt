package com.dev.app.routify.domain.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

fun LocalDateTime.toFormatted(
    formatter: DateTimeFormatter? = DEFAULT_FORMATTER
): String {
    return this.format(formatter)
}
