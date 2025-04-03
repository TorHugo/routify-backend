package com.dev.app.routify.infrastructure.gateway

import com.dev.app.routify.domain.entity.EmailTemplateDomain
import com.dev.app.routify.domain.gateway.EmailTemplateGateway
import com.dev.app.routify.infrastructure.repository.EmailTemplateRepository
import com.dev.app.routify.infrastructure.repository.mapper.toDomain
import com.dev.app.routify.infrastructure.repository.mapper.toEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EmailTemplateGatewayImpl(
    private val emailTemplateRepository: EmailTemplateRepository
) : EmailTemplateGateway {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    override fun save(domain: EmailTemplateDomain) {
        logger.info("c=EmailTemplateGatewayImpl m=save() s=start identifier=${domain.identifier}")
        emailTemplateRepository.save(domain.toEntity())
        logger.info("c=EmailTemplateGatewayImpl m=save() s=done identifier=${domain.identifier}")
    }

    override fun findByTemplateKeyAndVersionAndActive(templateKey: String, version: String, active: Boolean): EmailTemplateDomain? {
        logger.info("c=EmailTemplateGatewayImpl m=findByTemplateKeyAndVersionAndActive() s=start templateKey=$templateKey version=$version active=$active")
        val entity = emailTemplateRepository.findByTemplateKeyAndVersionAndActive(
            templateKey = templateKey,
            version = version,
            active = active
        )
        logger.info("c=EmailTemplateGatewayImpl m=findByTemplateKeyAndVersionAndActive() s=done templateKey=$templateKey version=$version active=$active")
        return entity?.toDomain()
    }

    override fun findByTemplateKeyAndLastActiveVersion(templateKey: String): EmailTemplateDomain? {
        logger.info("c=EmailTemplateGatewayImpl m=findByTemplateKeyAndLastActiveVersion() s=start templateKey=$templateKey")
        val entity = emailTemplateRepository.findByTemplateKeyAndLastActiveVersion(
            templateKey = templateKey
        )
        logger.info("c=EmailTemplateGatewayImpl m=findByTemplateKeyAndLastActiveVersion() s=done templateKey=$templateKey")
        return entity?.toDomain()
    }
}
