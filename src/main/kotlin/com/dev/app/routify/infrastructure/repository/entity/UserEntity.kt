package com.dev.app.routify.infrastructure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user_tb", schema = "routify_db")
open class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var userId: Long? = null,

    @Column(unique = true, nullable = false, updatable = false)
    open var externalId: UUID? = null,

    open var email: String? = null,
    open var password: String? = null,
    open var createdAt: LocalDateTime? = null,
    open var updatedAt: LocalDateTime? = null
)
