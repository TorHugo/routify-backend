package com.dev.app.routify.infrastructure.service.template

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.models.EmailTemplateDTO
import com.dev.app.routify.application.models.TokenDTO
import com.dev.app.routify.application.usecase.CreateTokenUseCase
import com.dev.app.routify.application.usecase.FindEmailTemplateUseCase
import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.enums.TypeNotificationEnum
import com.dev.app.routify.domain.enums.TypeTokenEnum
import com.dev.app.routify.domain.extension.toFormatted
import com.dev.app.routify.domain.service.PublisherEmailService
import com.dev.app.routify.infrastructure.service.AbstractCommunicationServiceImpl
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component("ResetCustomerPasswordCommunicationTemplate")
class ResetCustomerPasswordCommunicationTemplate(
    private val createTokenUseCase: CreateTokenUseCase,
    gson: Gson,
    publisherEmailService: PublisherEmailService,
    findEmailTemplateUseCase: FindEmailTemplateUseCase,
): AbstractCommunicationServiceImpl(
    gson = gson,
    publisherEmailService = publisherEmailService,
    findEmailTemplateUseCase = findEmailTemplateUseCase
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_NAME_PARAMETER: String = "@name"
        private const val DEFAULT_EXPIRATION_DATE_PARAMETER: String = "@expiration-date"
        private const val DEFAULT_HASHCODE_PARAMETER: String = "@hashcode"
    }

    override fun buildCommunicationBody(template: EmailTemplateDTO, user: UserDomain): String {
        logger.info("c=ResetCustomerPasswordCommunicationTemplate m=buildCommunicationBody() s=Start userId=${user.identifier}")
        val token = createTokenUseCase.execute(
            user = user.toApplicationDTO(),
            tokenType = TypeTokenEnum.TOKEN_RESET_CUSTOMER_PASSWORD.value
        )

        val parameters = this.createCommunicationParameters(
            user = user,
            token = token
        )

        val body = super.replaceBodyMessage(
            body = template.body,
            parameters = parameters
        )
        logger.info("c=ResetCustomerPasswordCommunicationTemplate m=buildCommunicationBody() s=Done userId=${user.identifier}")
        return body
    }

    private fun createCommunicationParameters(user: UserDomain, token: TokenDTO): Map<String, String> {
        val parameters = mutableMapOf<String, String>()
        parameters[DEFAULT_NAME_PARAMETER] = user.fullName()
        parameters[DEFAULT_EXPIRATION_DATE_PARAMETER] = token.expiration.toFormatted()
        parameters[DEFAULT_HASHCODE_PARAMETER] = token.tokenHash
        return parameters
    }

    override fun defineNotificationType(): String {
        return TypeNotificationEnum.SEND_CUSTOMER_RESET_PASSWORD.value
    }
}