package com.dev.app.routify.domain.extension

fun List<*>.toSafeListOfMaps(): List<Map<String, Any>> {
    return this.mapNotNull { item ->
        when (item) {
            is Map<*, *> -> item.entries.associate { (key, value) ->
                key.toString() to (value ?: Any())
            }
            else -> null
        }
    }
}