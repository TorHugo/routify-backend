package com.dev.app.routify.domain.gateway

import com.dev.app.routify.domain.entity.EmailTemplateDomain

interface EmailTemplateGateway {
    fun save(domain: EmailTemplateDomain)
    fun findByTemplateKeyAndVersionAndActive(templateKey: String, version: String, active: Boolean): EmailTemplateDomain?
    fun findByTemplateKeyAndLastActiveVersion(templateKey: String): EmailTemplateDomain?
}
