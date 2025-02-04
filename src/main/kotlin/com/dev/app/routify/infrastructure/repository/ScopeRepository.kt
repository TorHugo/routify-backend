package com.dev.app.routify.infrastructure.repository

import com.dev.app.routify.infrastructure.repository.entity.ScopeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ScopeRepository : JpaRepository<ScopeEntity, String> {

    @Query(
        """
        SELECT us
        FROM ScopeEntity us
        WHERE us.scopeId IN (:scopeKey)
    """
    )
    fun findAllByScopesKey(scopeKey: List<String>): List<ScopeEntity>
}
