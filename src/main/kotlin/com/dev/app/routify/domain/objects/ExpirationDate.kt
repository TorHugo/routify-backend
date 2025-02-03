package com.dev.app.routify.domain.objects

import java.time.LocalDateTime

data class ExpirationDate(val value: LocalDateTime) {
    companion object {
        fun generate(
            minutes: Long = 0,
            hours: Long = 0,
            days: Long = 0
        ): ExpirationDate {
            return ExpirationDate(
                LocalDateTime.now()
                    .plusMinutes(minutes)
                    .plusHours(hours)
                    .plusDays(days)
            )
        }
    }
}
