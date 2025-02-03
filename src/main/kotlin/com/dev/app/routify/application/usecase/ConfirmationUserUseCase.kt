package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.models.ConfirmationUserDTO
import com.dev.app.routify.domain.enums.StatusNotificationEnum
import com.dev.app.routify.domain.enums.TypeNotificationEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.exception.template.InternalServerException
import com.dev.app.routify.domain.extension.toLocalDateTime
import com.dev.app.routify.domain.gateway.NotificationGateway
import com.dev.app.routify.domain.gateway.UserGateway
import com.dev.app.routify.domain.objects.Parameter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ConfirmationUserUseCase(
    private val userGateway: UserGateway,
    private val notificationGateway: NotificationGateway,
    private val gson: Gson
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_KEY_HASH_CODE: String = "hashcode"
        private const val DEFAULT_KEY_EXPIRATION_DATE: String = "expiration-date"
        private val DEFAULT_TYPE_NOTIFICATION: TypeNotificationEnum = TypeNotificationEnum.SEND_CONFIRMATION_ACCOUNT
        private val DEFAULT_SENDING_STATUS_NOTIFICATION: StatusNotificationEnum = StatusNotificationEnum.SENDING
    }

    @Transactional
    fun execute(dto: ConfirmationUserDTO) {
        try {
            logger.info("c=ConfirmationUserUseCase m=execute() s=start email=${dto.email} hashcode=${dto.hashcode}")
            val user = userGateway.findByEmail(dto.email) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)

            val notification = notificationGateway.findByUserIdAndNotificationType(
                userId = user.identifier!!,
                type = DEFAULT_TYPE_NOTIFICATION
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_NOTIFICATION_EMAIL_NOT_FOUND.message)

            if (notification.status.value != DEFAULT_SENDING_STATUS_NOTIFICATION.value) {
                throw DomainException(ErrorMessageEnum.ERROR_NOTIFICATION_STATUS_IS_NOT_PENDING.message)
            }

            val currentDate = LocalDateTime.now()
            val parameterListType = object : TypeToken<List<Parameter>>() {}.type
            val parameters: List<Parameter> = gson.fromJson(notification.parameters, parameterListType)
            val savedHashCode = parameters.find { it.key == DEFAULT_KEY_HASH_CODE }
                ?: throw InternalServerException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
            val savedExpirationDate = parameters.find { it.key == DEFAULT_KEY_EXPIRATION_DATE }
                ?: throw InternalServerException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)

            if (savedHashCode.value != dto.hashcode) {
                throw DomainException(ErrorMessageEnum.ERROR_HASHCODE_DIFFERENT_TO_SENT_USER.message)
            }

            if (currentDate.isAfter(savedExpirationDate.value.toLocalDateTime())) {
                throw DomainException(ErrorMessageEnum.ERROR_HASHCODE_IS_EXPIRED.message)
            }

            user.confirmation()
            notification.confirmation()

            userGateway.save(
                domain = user
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
            throw GenericException(ErrorMessageEnum.ERROR_GENERIC.message)
        }
    }
}
