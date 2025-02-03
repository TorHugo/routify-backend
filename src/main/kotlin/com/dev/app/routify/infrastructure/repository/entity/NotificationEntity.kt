package com.dev.app.routify.infrastructure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "notification_tb", schema = "routify_db")
open class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var notificationId: Long? = null,

    open var userId: Long,
    open var type: String? = null,
    open var status: String? = null,
    open var subject: String? = null,
    @Column(columnDefinition = "TEXT")
    open var message: String? = null,
    @Column(columnDefinition = "TEXT")
    open var parameters: String? = null,
    open var createdAt: LocalDateTime? = null,
    open var updatedAt: LocalDateTime? = null
)
