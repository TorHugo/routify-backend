package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.application.models.EmailTemplateDTO
import com.dev.app.routify.application.usecase.FindEmailTemplateUseCase
import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.enums.StatusNotificationEnum
import com.dev.app.routify.domain.event.DomainEvent
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.objects.StatusNotification
import com.dev.app.routify.domain.objects.TypeNotification
import com.dev.app.routify.domain.service.CommunicationService
import com.dev.app.routify.domain.service.PublisherEmailService
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
abstract class AbstractCommunicationServiceImpl(
    private val gson: Gson,
    private val publisherEmailService: PublisherEmailService,
    private val findEmailTemplateUseCase: FindEmailTemplateUseCase
): CommunicationService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    override fun publish(event: DomainEvent) {
        try {
            logger.info("c=AbstractCommunicationServiceImpl m=publish() s=Start")
            if (event.subType == null){
                throw DomainException("event.sub.type.is.mandatory.and.not.null")
            }
            val template = findEmailTemplateUseCase.execute(templateKey = event.subType.emailTemplateKeyEnum.name)
            val user = gson.fromJson(event.domain.toString(), UserDomain::class.java)
            val body = this.buildCommunicationBody(template = template, user = user)
            val notificationType = this.defineNotificationType()

            val domain = NotificationDomain(
                userId = user.identifier!!,
                toEmail = user.email.value,
                status = StatusNotification(value = StatusNotificationEnum.SENDING.value),
                type = TypeNotification(value = notificationType),
                subject = template.subject,
                message = body
            )

            publisherEmailService.publish(
                domain = domain
            )
            logger.info("c=AbstractCommunicationServiceImpl m=publish() s=Done")
        } catch (ex: Exception) {
            logger.info("c=AbstractCommunicationServiceImpl m=publish() s=error-generic message=${ex.message}")
            throw GenericException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }

    protected fun replaceBodyMessage(body: String, parameters: Map<String, String>): String {
        var modifiedBody = body
        parameters.forEach { (key, value) ->
            modifiedBody = modifiedBody.replace("`$key`", value)
        }
        return modifiedBody
    }

    /** Each template should create parameters for sending, whether it's a single message or even confirmation tokens. **/
    abstract fun buildCommunicationBody(template: EmailTemplateDTO, user: UserDomain): String
    /** Each template should return the specific notification type. **/
    abstract fun defineNotificationType(): String
}