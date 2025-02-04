package com.dev.app.routify.application.usecase

import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserScopeGateway
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CreateUserScopeUseCase(
    private val userScopeGateway: UserScopeGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun execute(userId: Long, scopeId: String) {
        try {
            logger.info("c=CreateUserScopeUseCase m=execute() s=start userId=$userId")
            userScopeGateway.save(
                userId = userId,
                scopeKey = scopeId
            )
            logger.info("c=CreateUserScopeUseCase m=execute() s=done userId=$userId")
        } catch (ex: Exception) {
            logger.error("c=CreateUserScopeUseCase m=execute() s=generic-error userId=$userId message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
