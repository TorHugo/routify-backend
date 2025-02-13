package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.models.UserDTO
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.exception.template.ResourceNotFoundException
import com.dev.app.routify.domain.gateway.UserGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class FindUserWithThrowsUseCase(
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun execute(email: String): UserDTO {
        try {
            logger.info("c=FindUserUseCase m=execute() s=start email=$email")
            val user = userGateway.findByEmail(email)
                ?: throw ResourceNotFoundException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)
            logger.info("c=FindUserUseCase m=execute() s=done email=$email")
            return user.toApplicationDTO()
        } catch (ex: Exception) {
            logger.error("c=FindUserUseCase m=execute() s=generic-error email=$email message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        } catch (ex: ResourceNotFoundException) {
            logger.error("c=FindUserUseCase m=execute() s=resource-not-found-error email=$email message=${ex.message}")
            throw ResourceNotFoundException(ex.message)
        }
    }
}
