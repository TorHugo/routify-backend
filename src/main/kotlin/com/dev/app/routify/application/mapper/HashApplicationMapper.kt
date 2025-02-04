package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.TokenDTO
import com.dev.app.routify.domain.entity.TokenDomain

fun TokenDomain.toApplicationDTO(): TokenDTO {
    return TokenDTO(
        identifier = this.identifier,
        userId = this.userId,
        tokenHash = this.tokenHash.value,
        tokenType = this.tokenType.value,
        expiration = this.expiration.value
    )
}
