package com.dev.app.routify.infrastructure.repository.helpers

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
open class UserScopeHelper(
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "scope_id", nullable = false)
    val scopeId: String
)