package com.dev.app.routify.domain.extension

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val DEFAULT_EXPIRATION_DATE_TIME: Int = 15
private const val DEFAULT_DATE_PATTERN: String = "yyyy-MM-dd HH:mm:ss"
private val DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

fun LocalDateTime.toFormatted(
    formatter: DateTimeFormatter? = DEFAULT_FORMATTER
): String {
    return this.format(formatter)
}

fun String.toLocalDateTime(
    formatter: DateTimeFormatter = DEFAULT_FORMATTER
): LocalDateTime {
    return LocalDateTime.parse(this, formatter)
}

fun Date.expirationDate(
    expirationTime: Int? = DEFAULT_EXPIRATION_DATE_TIME
): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MINUTE, expirationTime!!)
    return calendar.time
}

fun Date.toFormatted(
    pattern: String? = DEFAULT_DATE_PATTERN,
    locale: Locale = Locale.getDefault()
): String {
    val formatter = SimpleDateFormat(pattern!!, locale)
    return formatter.format(this)
}
