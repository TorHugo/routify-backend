package com.dev.app.routify.infrastructure.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "token_tb", schema = "routify_db")
open class TokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var tokenId: Long? = null,
    open var userId: Long,
    open var tokenHash: String? = null,
    open var tokenType: String? = null,
    open var expiration: LocalDateTime? = null,
    open var used: Boolean? = null,
    open var createdAt: LocalDateTime? = null,
    open var updatedAt: LocalDateTime? = null
)
