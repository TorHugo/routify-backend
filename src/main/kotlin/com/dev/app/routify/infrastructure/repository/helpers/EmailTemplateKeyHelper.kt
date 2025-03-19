package com.dev.app.routify.infrastructure.repository.helpers

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
open class EmailTemplateKeyHelper(
    @Column(name = "template_key", nullable = false)
    val templateKey: String,

    @Column(name = "version", nullable = false)
    val version: String,

    @Column(name = "active", nullable = false)
    val active: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmailTemplateKeyHelper

        if (active != other.active) return false
        if (templateKey != other.templateKey) return false
        if (version != other.version) return false

        return true
    }

    override fun hashCode(): Int {
        var result = active.hashCode()
        result = 31 * result + templateKey.hashCode()
        result = 31 * result + version.hashCode()
        return result
    }
}
