package com.dev.app.routify.domain.gateway

import com.dev.app.routify.domain.entity.UserDomain

interface UserGateway {
    fun save(domain: UserDomain): UserDomain
    fun findByEmail(email: String): UserDomain?
}
