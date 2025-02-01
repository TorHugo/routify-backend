package com.dev.app.routify.infrastructure.gateway

import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.gateway.UserGateway
import com.dev.app.routify.infrastructure.repository.UserAttributesRepository
import com.dev.app.routify.infrastructure.repository.UserRepository
import com.dev.app.routify.infrastructure.repository.mapper.toAttributeEntity
import com.dev.app.routify.infrastructure.repository.mapper.toDomain
import com.dev.app.routify.infrastructure.repository.mapper.toEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserGatewayImpl(
    private val userRepository: UserRepository,
    private val userAttributesRepository: UserAttributesRepository
) : UserGateway {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    override fun save(domain: UserDomain): UserDomain {
        logger.info("c=UserGatewayImpl m=save() s=start: ${domain.email}")
        val entity = userRepository.save(domain.toEntity())
        val attributesEntity = userAttributesRepository.save(domain.toAttributeEntity(entity.userId!!))
        logger.info("c=UserGatewayImpl m=save() s=done: ${domain.email}")
        return entity.toDomain(attributes = attributesEntity)
    }

    override fun findByEmail(email: String): UserDomain? {
        logger.info("c=UserGatewayImpl m=findByEmail() s=start: $email")
        val entity = userRepository.findByEmail(email) ?: return null
        val attributesEntity = userAttributesRepository.findByUserId(entity.userId!!)
        logger.info("c=UserGatewayImpl m=findByEmail() s=done: $email")
        return entity.toDomain(attributesEntity)
    }
}
