package com.dev.app.routify.infrastructure.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "scope_tb", schema = "routify_db")
open class ScopeEntity(
    @Id
    open var scopeId: String? = null,
    open var path: String? = null,
    open var scopes: String? = null
)
