package com.dev.app.routify.infrastructure.event

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.usecase.CreateHashUseCase
import com.dev.app.routify.application.usecase.CreateNotificationUseCase
import com.dev.app.routify.application.usecase.SendNotificationEmailUseCase
import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.enums.StatusNotificationEnum
import com.dev.app.routify.domain.enums.TypeNotificationEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.extension.replaceAllParameters
import com.dev.app.routify.domain.extension.toFormatted
import com.dev.app.routify.domain.objects.Parameter
import com.dev.app.routify.domain.objects.StatusNotification
import com.dev.app.routify.domain.objects.TypeNotification
import com.dev.app.routify.infrastructure.event.models.ConfirmationCreatingAccountEventDTO
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ConfirmationCreatingAccountListener(
    private val notificationUseCase: SendNotificationEmailUseCase,
    private val createNotificationUseCase: CreateNotificationUseCase,
    private val createHashUseCase: CreateHashUseCase,
    private val gson: Gson
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_KEY_FULL_NAME: String = "name"
        private const val DEFAULT_KEY_HASH_CODE: String = "hashcode"
        private const val DEFAULT_KEY_EXPIRATION_DATE: String = "expiration-date"

        private val DEFAULT_NOTIFICATION_STATUS: StatusNotificationEnum = StatusNotificationEnum.SENDING
        private val DEFAULT_NOTIFICATION_TYPE: TypeNotificationEnum = TypeNotificationEnum.SEND_CONFIRMATION_ACCOUNT
    }

    @EventListener
    fun onConfirmationCreatingAccount(dto: ConfirmationCreatingAccountEventDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=start email=${dto.user.email.value}")
                val hash = createHashUseCase.execute(dto.user.email.value)

                val parameters = listOf(
                    Parameter(key = DEFAULT_KEY_FULL_NAME, value = dto.user.fullName()),
                    Parameter(key = DEFAULT_KEY_HASH_CODE, value = hash.identifier),
                    Parameter(key = DEFAULT_KEY_EXPIRATION_DATE, value = hash.expiration.toFormatted())
                )

                val mailTemplate = dto.template
                val subject = mailTemplate.subject
                val body = mailTemplate.message.replaceAllParameters(parameters)

                val notificationDomain = NotificationDomain(
                    userId = dto.user.identifier!!,
                    toEmail = dto.user.email.value,
                    status = StatusNotification(DEFAULT_NOTIFICATION_STATUS.value),
                    type = TypeNotification(DEFAULT_NOTIFICATION_TYPE.type),
                    subject = subject,
                    message = body,
                    parameters = gson.toJson(parameters)
                )

                notificationUseCase.execute(
                    notificationDomain.toApplicationDTO()
                )

                createNotificationUseCase.execute(
                    notificationDomain.toApplicationDTO()
                )
                logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=done email=${dto.user.email.value}")
            } catch (ex: DomainException) {
                logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=error-domain email=${dto.user.email.value} message=${ex.message}")
                throw DomainException(ex.message)
            } catch (ex: Exception) {
                // should be retry
                logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=error-generic email=${dto.user.email.value} message=${ex.message}")
                throw GenericException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
            }
        }
    }
}
