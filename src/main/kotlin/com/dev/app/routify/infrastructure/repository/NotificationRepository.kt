package com.dev.app.routify.infrastructure.repository

import com.dev.app.routify.infrastructure.repository.entity.NotificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotificationRepository : JpaRepository<NotificationEntity, Long> {
    @Query("select n from NotificationEntity n where n.userId = ?1 and n.type = ?2")
    fun findByUserIdAndType(userId: Long, type: String): NotificationEntity?
}
