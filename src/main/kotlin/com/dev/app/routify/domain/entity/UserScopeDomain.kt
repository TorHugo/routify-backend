package com.dev.app.routify.domain.entity

data class UserScopeDomain(
    val user: UserDomain,
    val scopes: List<ScopeDomain>
)
