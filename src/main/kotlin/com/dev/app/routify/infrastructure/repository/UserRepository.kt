package com.dev.app.routify.infrastructure.repository

import com.dev.app.routify.infrastructure.repository.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
    fun findByUserId(userId: Long): UserEntity?
}
