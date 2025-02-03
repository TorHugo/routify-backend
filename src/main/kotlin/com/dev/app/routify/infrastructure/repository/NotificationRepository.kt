package com.dev.app.routify.infrastructure.repository

import com.dev.app.routify.infrastructure.repository.entity.NotificationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<NotificationEntity, Long>
