package com.dev.app.routify.infrastructure.event

import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.usecase.CreateTokenUseCase
import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.extension.replaceAllParameters
import com.dev.app.routify.domain.extension.toFormatted
import com.dev.app.routify.domain.objects.Parameter
import com.dev.app.routify.domain.objects.StatusNotification
import com.dev.app.routify.domain.objects.TypeNotification
import com.dev.app.routify.domain.service.NotificationEmailService
import com.dev.app.routify.infrastructure.event.models.SendBaseNotificationEventDTO
import com.dev.app.routify.infrastructure.event.models.SendNotificationWithHashTokenEventDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SendNotificationWithHashTokenListener(
    private val createTokenUseCase: CreateTokenUseCase,
    private val notificationEmailService: NotificationEmailService
): SendBaseNotificationEvent(notificationEmailService) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_KEY_HASH_CODE: String = "hashcode"
        private const val DEFAULT_KEY_EXPIRATION_DATE: String = "expiration-date"
        private const val DEFAULT_THEAD_DELAY: Long = 500L
    }

    @EventListener
    fun onSendNotificationWithHashTokenListener(dto: SendNotificationWithHashTokenEventDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(DEFAULT_THEAD_DELAY)
            handleSendNotificationWithHashTokenListener(dto = dto)
        }
    }

    suspend fun handleSendNotificationWithHashTokenListener(dto: SendNotificationWithHashTokenEventDTO) {
        try {
            logger.info("c=SendNotificationWithHashTokenListener m=onSendNotificationWithHashTokenListener() s=start email=${dto.user.email} type=${dto.typeToken}")
            val token = createTokenUseCase.execute(dto.user, dto.typeToken.value)
            val defaultTokenParameters = listOf(
                Parameter(
                    key = DEFAULT_KEY_HASH_CODE,
                    value = token.tokenHash
                ),
                Parameter(
                    key = DEFAULT_KEY_EXPIRATION_DATE,
                    value = token.expiration.toFormatted()
                )
            )
            val parameters = dto.parameters.plus(defaultTokenParameters)
            val userDomain = dto.user.toDomain()

            val mailTemplate = dto.template
            val subject = mailTemplate.subject
            val body = mailTemplate.message.replaceAllParameters(parameters)

            val notificationDomain = NotificationDomain(
                userId = dto.user.identifier!!,
                toEmail = userDomain.email.value,
                status = StatusNotification(dto.statusNotification.value),
                type = TypeNotification(dto.typeNotification.value),
                subject = subject,
                message = body
            )

            notificationEmailService.execute(
                domain = notificationDomain
            )
            logger.info("c=SendNotificationWithHashTokenListener m=handleSendNotificationWithHashTokenListener() s=done email=${dto.user.email} type=${dto.typeToken}")
        } catch (ex: DomainException) {
            logger.info("c=SendNotificationWithHashTokenListener m=handleSendNotificationWithHashTokenListener() s=error-domain email=${dto.user.email} type=${dto.typeToken} message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.info("c=SendNotificationWithHashTokenListener m=handleSendNotificationWithHashTokenListener() s=error-generic email=${dto.user.email} type=${dto.typeToken} message=${ex.message}")
            throw GenericException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
