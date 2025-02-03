package com.dev.app.routify.infrastructure.repository.mapper

import com.dev.app.routify.domain.entity.ScopeDomain
import com.dev.app.routify.domain.enums.ScopeKeyEnum
import com.dev.app.routify.infrastructure.repository.entity.ScopeEntity

fun ScopeEntity.toDomain(): ScopeDomain {
    return ScopeDomain(
        identifier = ScopeKeyEnum.findValue(this.scopeId!!),
        path = this.path!!,
        scopes = this.scopes!!
    )
}