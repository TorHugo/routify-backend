package com.dev.app.routify.application.usecase

import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.TokenGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CleanExpirationEventsUseCase(
    private val tokenGateway: TokenGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun execute() {
        try {
            logger.info("c=CleanExpirationEventsUseCase m=execute() s=start")
            val expiredTokens = tokenGateway.findAllExpiredTokens()
            tokenGateway.deleteByBatch(expiredTokens)
            logger.info("c=CleanExpirationEventsUseCase m=execute() s=done")
        } catch (ex: Exception) {
            logger.error("c=CleanExpirationEventsUseCase m=execute() s=error-generic message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
