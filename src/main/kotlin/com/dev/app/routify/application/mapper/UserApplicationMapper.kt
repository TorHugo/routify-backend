package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.CreateUserDTO
import com.dev.app.routify.application.models.UserDTO
import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.objects.Email
import com.dev.app.routify.domain.objects.UUIDv4

fun CreateUserDTO.toDomain(): UserDomain {
    return UserDomain(
        email = Email(this.email),
        password = this.password,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber
    )
}

fun UserDTO.toDomain(): UserDomain {
    return UserDomain(
        identifier = this.identifier,
        externalId = UUIDv4(this.externalId!!),
        email = Email(this.email!!),
        firstName = this.firstName!!,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber!!,
        active = this.active,
        confirmed = this.confirmed,
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt
    )
}

fun UserDomain?.toApplicationDTO(): UserDTO? {
    if (this == null) return null
    return UserDTO(
        identifier = this.identifier,
        externalId = this.externalId.value,
        email = this.email.value,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        active = this.active,
        confirmed = this.confirmed,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
