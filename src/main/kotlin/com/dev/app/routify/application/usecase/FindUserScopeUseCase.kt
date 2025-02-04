package com.dev.app.routify.application.usecase

import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.entity.UserScopeDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserGateway
import com.dev.app.routify.domain.gateway.UserScopeGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FindUserScopeUseCase(
    private val userScopeGateway: UserScopeGateway,
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun execute(userDomain: UserDomain): UserScopeDomain? {
        try {
            logger.info("c=FindUserScopeUseCase m=execute() s=start email=${userDomain.email}")
            val scopes = userScopeGateway.findUserScope(userDomain)
            logger.info("c=FindUserScopeUseCase m=execute() s=done email=${userDomain.email}")
            return scopes
        } catch (ex: Exception) {
            logger.error("c=FindUserScopeUseCase m=execute() s=generic-error email=${userDomain.email} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
