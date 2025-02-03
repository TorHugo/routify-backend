package com.dev.app.routify.infrastructure.event

import com.dev.app.routify.application.usecase.CreateHashUseCase
import com.dev.app.routify.application.usecase.CreateNotificationUseCase
import com.dev.app.routify.application.usecase.FindUserUseCase
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
    private val findUserUseCase: FindUserUseCase,
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
                logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=start: ${dto.email}")
                val user = findUserUseCase.execute(dto.email) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)
                val hash = createHashUseCase.execute(dto.email)

                val parameters = listOf(
                    Parameter(key = DEFAULT_KEY_FULL_NAME, value = user.fullName()),
                    Parameter(key = DEFAULT_KEY_HASH_CODE, value = hash.identifier.value),
                    Parameter(key = DEFAULT_KEY_EXPIRATION_DATE, value = hash.expiration.value.toFormatted())
                )

                val mailTemplate = dto.template
                val subject = mailTemplate.subject
                val body = mailTemplate.message.replaceAllParameters(parameters)

                val notificationDomain = NotificationDomain(
                    userId = user.identifier!!,
                    toEmail = user.email.value,
                    status = StatusNotification(DEFAULT_NOTIFICATION_STATUS.value),
                    type = TypeNotification(DEFAULT_NOTIFICATION_TYPE.type),
                    subject = subject,
                    message = body,
                    parameters = gson.toJson(parameters)
                )

                notificationUseCase.execute(
                    notificationDomain
                )

                createNotificationUseCase.execute(
                    notificationDomain
                )
                logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=done: ${dto.email}")
            } catch (ex: Exception) {
                // should be retry
                logger.info("c=ConfirmationCreatingAccountListener m=onConfirmationCreatingAccount() s=error: ${dto.email} message=${ex.message}")
                throw GenericException(ex.message ?: ErrorMessageEnum.ERROR_GENERIC.message)
            }
        }
    }
}
