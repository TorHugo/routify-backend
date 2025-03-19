package com.dev.app.routify.infrastructure.repository.helpers

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
open class UserScopeHelper(
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "scope_id", nullable = false)
    val scopeId: String

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserScopeHelper

        if (userId != other.userId) return false
        if (scopeId != other.scopeId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + scopeId.hashCode()
        return result
    }
}
