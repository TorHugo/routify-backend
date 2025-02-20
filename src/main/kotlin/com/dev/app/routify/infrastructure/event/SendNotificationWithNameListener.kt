package com.dev.app.routify.infrastructure.event

import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.service.NotificationEmailService
import com.dev.app.routify.infrastructure.event.models.SendBaseNotificationEventDTO
import com.dev.app.routify.infrastructure.event.models.SendNotificationWithNameEventDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SendNotificationWithNameListener(
    private val notificationEmailService: NotificationEmailService
): SendBaseNotificationEvent(notificationEmailService) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_THEAD_DELAY: Long = 500L
    }

    @EventListener
    fun onSendEmailWithParams(dto: SendNotificationWithNameEventDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(DEFAULT_THEAD_DELAY)
            handleSendEmailWithParams(dto = dto)
        }
    }

    suspend fun handleSendEmailWithParams(dto: SendNotificationWithNameEventDTO) {
        try {
            logger.info("c=SendNotificationWithNameListener m=handleSendEmailWithParams() s=start email=${dto.user.email}")


            super.sendEmail(dto =
                SendBaseNotificationEventDTO(
                    transaction = dto.transaction,
                    user = dto.user,
                    typeNotification = dto.typeNotification,
                    template = dto.template,
                    parameters = dto.parameters
                )
            )
            logger.info("c=SendNotificationWithHashTokenListener m=handleSendEmailWithParams() s=done email=${dto.user.email}")
        } catch (ex: DomainException) {
            logger.info("c=SendNotificationWithHashTokenListener m=handleSendEmailWithParams() s=error-domain email=${dto.user.email} message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.info("c=SendNotificationWithHashTokenListener m=handleSendEmailWithParams() s=error-generic email=${dto.user.email} message=${ex.message}")
            throw GenericException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}