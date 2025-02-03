package com.dev.app.routify.infrastructure.gateway

import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.entity.UserScopeDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.gateway.UserScopeGateway
import com.dev.app.routify.infrastructure.repository.ScopeRepository
import com.dev.app.routify.infrastructure.repository.UserRepository
import com.dev.app.routify.infrastructure.repository.UserScopeRepository
import com.dev.app.routify.infrastructure.repository.entity.UserScopeEntity
import com.dev.app.routify.infrastructure.repository.helpers.UserScopeHelper
import com.dev.app.routify.infrastructure.repository.mapper.toDomain
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserScopeGatewayImpl(
    private val userScopeRepository: UserScopeRepository,
    private val scopeRepository: ScopeRepository,
    private val userRepository: UserRepository
): UserScopeGateway {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun save(userId: Long, scopeKey: String) {
        logger.info("c=ScopeGatewayImpl m=save() s=start userId=$userId scopeKey=${scopeKey}")
        val currentDate = LocalDateTime.now()
        val entity = userScopeRepository.save(
            UserScopeEntity(
                userScopeId = UserScopeHelper(
                    userId = userId,
                    scopeId = scopeKey
                ),
                createdAt = currentDate
            )
        )
        userScopeRepository.save(entity)
        logger.info("c=ScopeGatewayImpl m=save() s=done userId=$userId scopeKey=${scopeKey}")
    }

    override fun findUserScope(userDomain: UserDomain): UserScopeDomain {
        logger.info("c=ScopeGatewayImpl m=findUserScope() s=start userId=${userDomain.identifier}")
        val scopesKey = userScopeRepository.findAllByUserId(userDomain.identifier!!)
            .map { it.userScopeId.scopeId }
        val scopes = scopeRepository.findAllByScopesKey(scopesKey)
        logger.info("c=ScopeGatewayImpl m=findUserScope() s=done userId=${userDomain.identifier}")
        return UserScopeDomain(
            user = userDomain,
            scopes = scopes.map {
                it.toDomain()
            }
        )
    }
}