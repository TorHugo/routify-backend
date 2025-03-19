package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.models.EmailTemplateDTO
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.EmailTemplateGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FindEmailTemplateUseCase(
    private val emailTemplateGateway: EmailTemplateGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun execute(templateKey: String): EmailTemplateDTO {
        try {
            logger.info("c=FindEmailTemplateUseCase m=execute() s=start templateKey=$templateKey")
            val existingTemplate = emailTemplateGateway.findByTemplateKeyAndLastActiveVersion(
                templateKey = templateKey
            )

            if (existingTemplate == null) {
                throw DomainException(ErrorMessageEnum.ERROR_EMAIL_TEMPLATE_NOT_FOUND.message)
            }

            logger.info("c=FindEmailTemplateUseCase m=execute() s=done templateKey=$templateKey")
            return existingTemplate.toApplicationDTO()
        } catch (ex: DomainException) {
            logger.error("c=FindEmailTemplateUseCase m=execute() s=domain-error templateKey=$templateKey message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=FindEmailTemplateUseCase m=execute() s=generic-error templateKey=$templateKey message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
