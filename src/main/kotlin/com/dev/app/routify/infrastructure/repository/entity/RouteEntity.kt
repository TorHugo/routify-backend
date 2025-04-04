package com.dev.app.routify.infrastructure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "route_tb", schema = "routify_db")
open class RouteEntity(
    @Id
    open var routeId: UUID? = null,

    @Column(unique = true, nullable = false, updatable = false)
    open var userId: Long? = null,

    open var status: String? = null,
    open var createdAt: LocalDateTime? = null,
    open var updatedAt: LocalDateTime? = null
)
