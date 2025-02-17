package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.ScopeDTO
import com.dev.app.routify.application.models.UserScopeDTO
import com.dev.app.routify.domain.entity.ScopeDomain
import com.dev.app.routify.domain.entity.UserScopeDomain

fun UserScopeDomain.toApplicationDTO(): UserScopeDTO {
    return UserScopeDTO(
        user = this.user.toApplicationDTO(),
        scopes = this.scopes.map { it.toApplicationDTO() }
    )
}

fun ScopeDomain.toApplicationDTO(): ScopeDTO {
    return ScopeDTO(
        identifier = this.identifier!!,
        path = this.path,
        scopes = this.scopes
    )
}
