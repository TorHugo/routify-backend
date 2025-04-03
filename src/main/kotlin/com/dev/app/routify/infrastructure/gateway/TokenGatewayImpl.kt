package com.dev.app.routify.infrastructure.gateway

import com.dev.app.routify.domain.entity.TokenDomain
import com.dev.app.routify.domain.gateway.TokenGateway
import com.dev.app.routify.infrastructure.repository.TokenRepository
import com.dev.app.routify.infrastructure.repository.mapper.toDomain
import com.dev.app.routify.infrastructure.repository.mapper.toEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class TokenGatewayImpl(
    private val tokenRepository: TokenRepository
) : TokenGateway {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    override fun save(domain: TokenDomain) {
        logger.info("c=TokenGatewayImpl m=save() s=start identifier=${domain.userId}")
        tokenRepository.save(domain.toEntity())
        logger.info("c=TokenGatewayImpl m=save() s=done identifier=${domain.userId}")
    }

    override fun delete(domain: TokenDomain) {
        logger.info("c=TokenGatewayImpl m=delete() s=start identifier=${domain.userId}")
        tokenRepository.delete(domain.toEntity())
        logger.info("c=TokenGatewayImpl m=delete() s=done identifier=${domain.userId}")
    }

    override fun findByUserIdAndTokenType(
        userId: Long,
        type: String
    ): TokenDomain? {
        logger.info("c=TokenGatewayImpl m=findByUserIdAndTokenType() s=start userId=$userId")
        val entity = tokenRepository.findByUserIdAndType(
            userId = userId,
            type = type
        )
        logger.info("c=TokenGatewayImpl m=findByUserIdAndTokenType() s=done userId=$userId")
        return entity?.toDomain()
    }

    override fun findByUserIdAndHashToken(userId: Long, hashcode: String): TokenDomain? {
        logger.info("c=TokenGatewayImpl m=findByUserIdAndHashToken() s=start userId=$userId")
        val entity = tokenRepository.findByUserIdAndTokenHash(
            userId = userId,
            hashcode = hashcode
        )
        logger.info("c=TokenGatewayImpl m=findByUserIdAndHashToken() s=done userId=$userId")
        return entity?.toDomain()
    }

    override fun findByUserIdAndTokenTypeAndUsed(userId: Long, type: String, used: Boolean): TokenDomain? {
        logger.info("c=TokenGatewayImpl m=findByUserIdAndTokenTypeAndUsed() s=start userId=$userId type=$type used=$used")
        val entity = tokenRepository.findByUserIdAndTypeAndUsed(
            userId = userId,
            type = type,
            used = used
        )
        logger.info("c=TokenGatewayImpl m=findByUserIdAndTokenTypeAndUsed() s=done userId=$userId type=$type used=$used")
        return entity?.toDomain()
    }

    override fun findAllExpiredTokens(): List<TokenDomain?> {
        logger.info("c=TokenGatewayImpl m=findAllExpiredTokens() s=start")
        val entities = tokenRepository.findAllExpired()
        logger.info("c=TokenGatewayImpl m=findAllExpiredTokens() s=done")
        return entities.map { it?.toDomain() }
    }

    override fun deleteByBatch(batch: List<TokenDomain?>) {
        logger.info("c=TokenGatewayImpl m=deleteByBatch() s=start tokens=${batch.size}")
        tokenRepository.deleteAllById(batch.map { it?.identifier })
        logger.info("c=TokenGatewayImpl m=deleteByBatch() s=done tokens=${batch.size}")
    }
}
