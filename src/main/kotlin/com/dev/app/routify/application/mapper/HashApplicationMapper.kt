package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.HashDTO
import com.dev.app.routify.domain.entity.HashDomain

fun HashDomain.toApplicationDTO(): HashDTO {
    return HashDTO(
        identifier = this.identifier.value,
        expiration = this.expiration.value
    )
}
