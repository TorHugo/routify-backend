package com.dev.app.routify.infrastructure.repository.entity

import com.dev.app.routify.infrastructure.repository.helpers.UserScopeHelper
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_scope_tb", schema = "routify_db")
open class UserScopeEntity(
    @EmbeddedId
    open var userScopeId: UserScopeHelper,
    open var createdAt: LocalDateTime? = null,
    open var updatedAt: LocalDateTime? = null
)
