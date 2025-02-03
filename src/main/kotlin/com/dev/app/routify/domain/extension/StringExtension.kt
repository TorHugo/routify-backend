package com.dev.app.routify.domain.extension

import com.dev.app.routify.domain.objects.Parameter

fun String.replaceParameters(
    key: String,
    value: String
): String {
    return this.replace(oldValue = "`@$key`", newValue = value)
}

fun String.replaceAllParameters(
    parameters: List<Parameter>
): String {
    var replacedValue = this
    parameters.forEach { (key, value) -> replacedValue = replacedValue.replaceParameters(key, value) }
    return replacedValue
}
