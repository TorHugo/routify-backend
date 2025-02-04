package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.models.UserDTO
import com.dev.app.routify.application.models.UserScopeDTO
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserScopeGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FindUserScopeUseCase(
    private val userScopeGateway: UserScopeGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun execute(dto: UserDTO): UserScopeDTO? {
        try {
            logger.info("c=FindUserScopeUseCase m=execute() s=start email=${dto.email}")
            val userDomain = dto.toDomain()
            val scopes = userScopeGateway.findUserScope(userDomain)
            logger.info("c=FindUserScopeUseCase m=execute() s=done email=${dto.email}")
            return scopes.toApplicationDTO()
        } catch (ex: Exception) {
            logger.error("c=FindUserScopeUseCase m=execute() s=generic-error email=${dto.email} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
