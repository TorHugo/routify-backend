package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.models.UpdateUserDTO
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserGateway
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UpdateUserUseCase(
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun execute(currentEmail: String, dto: UpdateUserDTO) {
        try {
            logger.info("c=UpdateUserUseCase m=execute() s=start email=$currentEmail")
            val existsUser = userGateway.findByEmail(
                email = currentEmail
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)

            existsUser.update(
                firstName = dto.firstName,
                lastName = dto.lastName,
                phoneNumber = dto.phoneNumber
            )

            userGateway.save(
                domain = existsUser
            )
            logger.info("c=UpdateUserUseCase m=execute() s=done email=$currentEmail")
        } catch (ex: DomainException) {
            logger.error("c=UpdateUserUseCase m=execute() s=domain-error email=$currentEmail message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=UpdateUserUseCase m=execute() s=generic-error email=$currentEmail message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
