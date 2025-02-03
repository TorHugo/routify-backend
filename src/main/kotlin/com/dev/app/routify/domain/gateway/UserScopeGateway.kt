package com.dev.app.routify.domain.gateway

import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.entity.UserScopeDomain

interface UserScopeGateway {
    fun save(userId: Long, scopeKey: String)
    fun findUserScope(userDomain: UserDomain): UserScopeDomain
}