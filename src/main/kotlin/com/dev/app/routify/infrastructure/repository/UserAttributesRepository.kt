package com.dev.app.routify.infrastructure.repository

import com.dev.app.routify.infrastructure.repository.entity.UserAttributesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserAttributesRepository : JpaRepository<UserAttributesEntity, Long> {
    fun findByUserId(userId: Long): UserAttributesEntity
}
