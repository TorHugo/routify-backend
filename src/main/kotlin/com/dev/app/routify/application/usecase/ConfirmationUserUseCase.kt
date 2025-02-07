package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.models.ConfirmationUserDTO
import com.dev.app.routify.domain.enums.TypeNotificationEnum
import com.dev.app.routify.domain.enums.TypeTokenEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.exception.template.InternalServerException
import com.dev.app.routify.domain.gateway.NotificationGateway
import com.dev.app.routify.domain.gateway.TokenGateway
import com.dev.app.routify.domain.gateway.UserGateway
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ConfirmationUserUseCase(
    private val userGateway: UserGateway,
    private val notificationGateway: NotificationGateway,
    private val tokenGateway: TokenGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private val DEFAULT_TYPE_TOKEN: TypeTokenEnum = TypeTokenEnum.TOKEN_CONFIRMATION_ACCOUNT
        private val DEFAULT_TYPE_NOTIFICATION: TypeNotificationEnum = TypeNotificationEnum.SEND_CONFIRMATION_ACCOUNT
    }

    @Transactional
    fun execute(dto: ConfirmationUserDTO) {
        try {
            logger.info("c=ConfirmationUserUseCase m=execute() s=start email=${dto.email} hashcode=${dto.hashcode}")
            val user = userGateway.findByEmail(dto.email) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)

            val token = tokenGateway.findByUserIdAndTokenType(
                userId = user.identifier!!,
                type = DEFAULT_TYPE_TOKEN.value
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_TOKEN_NOT_FOUND.message)

            val notification = notificationGateway.findByUserIdAndNotificationType(
                userId = user.identifier,
                type = DEFAULT_TYPE_NOTIFICATION.value
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_NOTIFICATION_EMAIL_NOT_FOUND.message)

            if (token.used!!) {
                throw DomainException(ErrorMessageEnum.ERROR_TOKEN_ALREADY_USED.message)
            }

            val currentDate = LocalDateTime.now()

            if (token.tokenHash.value != dto.hashcode) {
                throw DomainException(ErrorMessageEnum.ERROR_TOKEN_DIFFERENT_TO_SENT_USER.message)
            }

            if (currentDate.isAfter(token.expiration.value)) {
                throw DomainException(ErrorMessageEnum.ERROR_TOKEN_IS_EXPIRED.message)
            }

            user.confirmation()
            token.confirmation()
            notification.confirmation()

            userGateway.save(
                domain = user
            )
            tokenGateway.save(
                domain = token
            )
            notificationGateway.save(
                domain = notification
            )
            logger.info("c=ConfirmationUserUseCase m=execute() s=done email=${dto.email} hashcode=${dto.hashcode}")
        } catch (ex: DomainException) {
            logger.error("c=ConfirmationUserUseCase m=execute() s=error-domain email=${dto.email} message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: InternalServerException) {
            logger.error("c=ConfirmationUserUseCase m=execute() s=error-internal email=${dto.email} message=${ex.message}")
            throw InternalServerException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=ConfirmationUserUseCase m=execute() s=error-generic email=${dto.email} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
