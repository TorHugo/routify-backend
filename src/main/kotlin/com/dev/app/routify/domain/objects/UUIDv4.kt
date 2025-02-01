package com.dev.app.routify.domain.objects

import java.util.*

data class UUIDv4(val value: UUID) {
    companion object {
        fun generate(): UUIDv4 {
            return UUIDv4(UUID.randomUUID())
        }
    }
}