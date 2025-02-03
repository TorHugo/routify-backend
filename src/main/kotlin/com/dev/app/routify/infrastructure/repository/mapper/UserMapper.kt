package com.dev.app.routify.infrastructure.repository.mapper

import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.objects.Email
import com.dev.app.routify.domain.objects.UUIDv4
import com.dev.app.routify.infrastructure.repository.entity.UserAttributesEntity
import com.dev.app.routify.infrastructure.repository.entity.UserEntity

fun UserDomain.toEntity(): UserEntity {
    return UserEntity(
        userId = this.identifier,
        externalId = this.externalId.value,
        email = this.email.value,
        password = this.password,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun UserDomain.toAttributeEntity(userId: Long): UserAttributesEntity {
    return UserAttributesEntity(
        userId = userId,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        active = this.active,
        confirmed = this.confirmed,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun UserEntity.toDomain(attributes: UserAttributesEntity): UserDomain {
    return UserDomain(
        identifier = this.userId,
        externalId = UUIDv4(this.externalId!!),
        email = Email(this.email!!),
        password = this.password!!,
        firstName = attributes.firstName!!,
        lastName = attributes.lastName,
        phoneNumber = attributes.phoneNumber!!,
        active = attributes.active!!,
        confirmed = attributes.confirmed!!,
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt
    )
}
