package com.dev.app.routify.domain.enums

enum class StatusRouteEnum(
    val value: String
) {
    STARTED(value = "STARTED"),
    FINISHED(value = "FINISHED");

    companion object {
        fun isExistsValue(
            value: String
        ): Boolean {
            return entries.toTypedArray().any { it.value == value }
        }
    }
}
