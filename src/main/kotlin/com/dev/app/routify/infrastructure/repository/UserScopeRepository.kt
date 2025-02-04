package com.dev.app.routify.infrastructure.repository

import com.dev.app.routify.infrastructure.repository.entity.UserScopeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserScopeRepository : JpaRepository<UserScopeEntity, String> {

    @Query(
        """
        SELECT us
        FROM UserScopeEntity us
        WHERE us.userScopeId.userId = :userId
    """
    )
    fun findAllByUserId(userId: Long): List<UserScopeEntity>
}
