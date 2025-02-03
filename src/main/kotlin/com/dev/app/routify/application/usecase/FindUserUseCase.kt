package com.dev.app.routify.application.usecase

import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class FindUserUseCase(
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun execute(email: String): UserDomain? {
        try {
            logger.info("c=FindUserUseCase m=execute() s=start: email=$email")
            val user = userGateway.findByEmail(email)
            logger.info("c=FindUserUseCase m=execute() s=done: email=$email")
            return user
        } catch (ex: Exception) {
            logger.error("c=FindUserUseCase m=execute() s=generic-error email=$email message=${ex.message}")
            throw GenericException(ErrorMessageEnum.ERROR_GENERIC.message)
        }
    }
}
