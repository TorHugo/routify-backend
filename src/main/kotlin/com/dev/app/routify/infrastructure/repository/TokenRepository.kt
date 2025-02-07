package com.dev.app.routify.infrastructure.repository

import com.dev.app.routify.infrastructure.repository.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TokenRepository : JpaRepository<TokenEntity, Long> {
    @Query("select n from TokenEntity n where n.userId = ?1 and n.tokenType = ?2")
    fun findByUserIdAndType(userId: Long, type: String): TokenEntity?

    @Query("select n from TokenEntity n where n.userId = ?1 and n.tokenHash = ?2 order by n.createdAt desc limit 1")
    fun findByUserIdAndTokenHash(userId: Long, hashcode: String): TokenEntity?
}
