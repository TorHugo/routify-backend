package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.application.models.PublishingEmailDTO
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.service.MailService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    private val mailSender: JavaMailSender
) : MailService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        private const val DEFAULT_MULTI_PART: Boolean = true
        private const val DEFAULT_UTF: String = "UTF-8"
    }

    override fun publishEmail(dto: PublishingEmailDTO) {
        try {
            logger.info("c=MailServiceImpl m=publishEmail() s=start email=${dto.to}")
            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, DEFAULT_MULTI_PART, DEFAULT_UTF)

            helper.setTo(dto.to)
            helper.setSubject(dto.subject)
            helper.setText(dto.body, dto.isHtml)
            dto.attachments.forEach { helper.addAttachment(it.name, it) }

            mailSender.send(message)
            logger.info("c=MailServiceImpl m=publishEmail() s=done email=${dto.to}")
        } catch (ex: Exception) {
            logger.error("c=MailServiceImpl m=publishEmail() s=error-generic email=${dto.to} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
