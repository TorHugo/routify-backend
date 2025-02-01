package com.dev.app.routify.infrastructure.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "user_attributes_tb", schema = "routify_db")
@Entity
open class UserAttributesEntity(
    @Id
    open var userId: Long? = null,
    open var firstName: String? = null,
    open var lastName: String? = null,
    open var phoneNumber: String? = null,
    open var active: Boolean? = null,
    open var confirmed: Boolean? = null,
    open var createdAt: LocalDateTime? = null,
    open var updatedAt: LocalDateTime? = null
)
