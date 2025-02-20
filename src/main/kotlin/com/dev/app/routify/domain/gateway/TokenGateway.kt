package com.dev.app.routify.domain.gateway

import com.dev.app.routify.domain.entity.TokenDomain

interface TokenGateway {
    fun save(domain: TokenDomain)

    fun delete(domain: TokenDomain)

    fun findByUserIdAndTokenType(
        userId: Long,
        type: String
    ): TokenDomain?

    fun findByUserIdAndHashToken(
        userId: Long,
        hashcode: String
    ): TokenDomain?

    fun findByUserIdAndTokenTypeAndUsed(
        userId: Long,
        type: String,
        used: Boolean
    ): TokenDomain?
}
