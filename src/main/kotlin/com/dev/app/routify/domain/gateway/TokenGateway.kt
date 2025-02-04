package com.dev.app.routify.domain.gateway

import com.dev.app.routify.domain.entity.TokenDomain

interface TokenGateway {
    fun save(domain: TokenDomain)
    fun findByUserIdAndTokenType(
        userId: Long,
        type: String
    ): TokenDomain?
}
