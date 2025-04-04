package com.dev.app.routify.infrastructure.repository

import com.dev.app.routify.infrastructure.repository.entity.RouteEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RouteRepository : JpaRepository<RouteEntity, Long> {

    @Query(
        nativeQuery = true,
        value = """
            SELECT entity.* FROM routify_db.route_tb entity
            WHERE entity.user_id = :userId
            AND entity.status != :status
            ORDER BY entity.created_at
        """
    )
    fun findAllUnfinishedRoutes(userId: Long, status: String): List<RouteEntity>
}
