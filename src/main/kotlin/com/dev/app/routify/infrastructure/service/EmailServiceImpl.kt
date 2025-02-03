package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.domain.entity.EmailDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.exception.template.ServiceException
import com.dev.app.routify.domain.service.EmailService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(
    private val mailSender: JavaMailSender
) : EmailService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        private const val DEFAULT_MULTI_PART: Boolean = true
        private const val DEFAULT_UTF: String = "UTF-8"
    }

    override fun sendEmail(domain: EmailDomain) {
        try {
            logger.info("c=EmailServiceImpl m=sendEmail() s=start email=${domain.to}")
            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, DEFAULT_MULTI_PART, DEFAULT_UTF)

            if (domain.to.isNullOrEmpty() ||
                domain.subject.isNullOrEmpty() ||
                domain.body.isNullOrEmpty()
            ) {
                throw ServiceException(ErrorMessageEnum.ERROR_GENERIC.message)
            }

            helper.setTo(domain.to)
            helper.setSubject(domain.subject)
            helper.setText(domain.body, domain.isHtml)
            domain.attachments.forEach { helper.addAttachment(it.name, it) }

            mailSender.send(message)
            logger.info("c=EmailServiceImpl m=sendEmail() s=done email=${domain.to}")
        } catch (ex: Exception) {
            logger.error("c=EmailServiceImpl m=sendEmail() s=error-generic email=${domain.to} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.ERROR_GENERIC.message)
        }
    }
}
