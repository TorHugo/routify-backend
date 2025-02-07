package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.models.UpdateUserPasswordDTO
import com.dev.app.routify.application.models.UserDTO
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserGateway
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UpdateUserPasswordUseCase(
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun execute(user: UserDTO, dto: UpdateUserPasswordDTO) {
        try {
            logger.info("c=UpdateUserPasswordUseCase m=execute() s=start email=${user.email}")
            if (!dto.matches) {
                throw DomainException(ErrorMessageEnum.ERROR_AUTHENTICATION_INVALID_ARGUMENTS.message)
            }
            val domain = user.toDomain()
            domain.resetPassword(dto.newPassword)

            userGateway.save(
                domain = domain
            )
            logger.info("c=UpdateUserPasswordUseCase m=execute() s=done email=${user.email}")
        } catch (ex: DomainException) {
            logger.error("c=UpdateUserPasswordUseCase m=execute() s=domain-error email=${user.email} message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=UpdateUserPasswordUseCase m=execute() s=generic-error email=${user.email} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
