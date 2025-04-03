package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.models.EmailTemplateDTO
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.EmailTemplateGateway
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CreateEmailTemplateUseCase(
    private val emailTemplateGateway: EmailTemplateGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun execute(dto: EmailTemplateDTO) {
        try {
            logger.info("c=CreateTemplateEmailUseCase m=execute() s=start templateKey=${dto.templateKey}")
            val existingTemplate = emailTemplateGateway.findByTemplateKeyAndVersionAndActive(
                templateKey = dto.templateKey,
                active = dto.active,
                version = dto.version
            )

            if (existingTemplate != null) {
                throw DomainException(ErrorMessageEnum.ERROR_EMAIL_TEMPLATE_ALREADY_EXISTS.message)
            }

            emailTemplateGateway.save(dto.toDomain())
            logger.info("c=CreateTemplateEmailUseCase m=execute() s=done templateKey=${dto.templateKey}")
        } catch (ex: DomainException) {
            logger.error("c=CreateTemplateEmailUseCase m=execute() s=domain-error templateKey=${dto.templateKey} message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=CreateTemplateEmailUseCase m=execute() s=generic-error templateKey=${dto.templateKey} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
