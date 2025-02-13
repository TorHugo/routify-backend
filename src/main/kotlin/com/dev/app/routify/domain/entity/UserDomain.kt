package com.dev.app.routify.domain.entity

import com.dev.app.routify.domain.objects.Email
import com.dev.app.routify.domain.objects.UUIDv4
import java.io.Serializable
import java.time.LocalDateTime

data class UserDomain(
    override val identifier: Long? = null,
    val externalId: UUIDv4 = UUIDv4.generate(),
    val email: Email,
    var password: String? = null,
    var firstName: String,
    var lastName: String? = null,
    var phoneNumber: String,
    val active: Boolean = true,
    var confirmed: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
) : Serializable, AggregateRoot<Long?>() {
    fun fullName(): String {
        return "${this.firstName} ${this.lastName}"
    }

    fun confirmation() {
        this.confirmed = true
        this.updatedAt = LocalDateTime.now()
    }

    fun resetPassword(newPassword: String) {
        this.password = newPassword
    }

    fun update(
        firstName: String?,
        lastName: String?,
        phoneNumber: String?
    ) {
        this.firstName = firstName ?: this.firstName
        this.lastName = lastName ?: this.lastName
        this.phoneNumber = phoneNumber ?: this.phoneNumber
    }
}
