package com.dev.app.routify.domain.entity

import com.dev.app.routify.domain.objects.Email
import com.dev.app.routify.domain.objects.UUIDv4
import java.io.Serializable
import java.time.LocalDateTime

data class UserDomain(
    override val identifier: Long? = null,
    val externalId: UUIDv4 = UUIDv4.generate(),
    val email: Email,
    val password: String,
    val firstName: String,
    val lastName: String? = null,
    val phoneNumber: String,
    val active: Boolean = true,
    var confirmed: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null
) : Serializable, AggregateRoot<Long?>() {
    fun fullName(): String {
        return "${this.firstName} ${this.lastName}"
    }

    fun confirmation() {
        this.confirmed = true
    }
}
