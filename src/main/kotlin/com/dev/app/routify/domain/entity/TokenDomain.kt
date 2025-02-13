package com.dev.app.routify.domain.entity

import com.dev.app.routify.domain.objects.ExpirationDate
import com.dev.app.routify.domain.objects.GenericHash
import com.dev.app.routify.domain.objects.TypeToken
import java.io.Serializable
import java.time.LocalDateTime

data class TokenDomain(
    override val identifier: Long? = null,
    val userId: Long,
    val tokenHash: GenericHash,
    val tokenType: TypeToken,
    val expiration: ExpirationDate,
    var used: Boolean? = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
) : Serializable, AggregateRoot<Long?>() {
    fun confirmation() {
        this.used = true
        this.updatedAt = LocalDateTime.now()
    }
}
