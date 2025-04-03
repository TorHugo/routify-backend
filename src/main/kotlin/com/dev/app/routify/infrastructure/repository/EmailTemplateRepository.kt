package com.dev.app.routify.infrastructure.repository

import com.dev.app.routify.infrastructure.repository.entity.EmailTemplateEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EmailTemplateRepository : JpaRepository<EmailTemplateEntity, String> {

    @Query(
        nativeQuery = true,
        value = """
            SELECT * FROM routify_db.email_template_tb etb
            WHERE etb.template_key = :templateKey 
            AND etb.version = :version
            AND etb.active = :active
        """
    )
    fun findByTemplateKeyAndVersionAndActive(templateKey: String, version: String, active: Boolean): EmailTemplateEntity?

    @Query(
        nativeQuery = true,
        value = """
            SELECT * FROM routify_db.email_template_tb etb
            WHERE etb.template_key = :templateKey
            AND etb.active = true
            ORDER BY etb.version DESC
            LIMIT 1
        """
    )
    fun findByTemplateKeyAndLastActiveVersion(templateKey: String): EmailTemplateEntity?
}
