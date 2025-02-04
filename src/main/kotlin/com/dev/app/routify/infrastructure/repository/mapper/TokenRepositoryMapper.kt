package com.dev.app.routify.infrastructure.repository.mapper

import com.dev.app.routify.domain.entity.TokenDomain
import com.dev.app.routify.domain.objects.ExpirationDate
import com.dev.app.routify.domain.objects.GenericHash
import com.dev.app.routify.domain.objects.TypeToken
import com.dev.app.routify.infrastructure.repository.entity.TokenEntity

fun TokenDomain.toEntity(): TokenEntity {
    return TokenEntity(
        tokenId = this.identifier,
        userId = this.userId,
        tokenHash = this.tokenHash.value,
        tokenType = this.tokenType.value,
        expiration = this.expiration.value,
        used = this.used,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun TokenEntity.toDomain(): TokenDomain {
    return TokenDomain(
        identifier = this.tokenId,
        userId = this.userId,
        tokenHash = GenericHash(this.tokenHash!!),
        tokenType = TypeToken(this.tokenType!!),
        expiration = ExpirationDate(this.expiration!!),
        used = this.used,
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt
    )
}
