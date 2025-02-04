package com.dev.app.routify.application.usecase

import com.dev.app.routify.domain.entity.HashDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.objects.ExpirationDate
import com.dev.app.routify.domain.objects.GenericHash
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CreateHashUseCase {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_EXPIRATION_MINUTES_TIME: Long = 15L
    }

    fun execute(identifier: String): HashDomain {
        try {
            logger.info("c=CreateHashUseCase m=execute() s=start identifier=$identifier")
            val hash = HashDomain(
                identifier = GenericHash.generate(),
                expiration = ExpirationDate.generate(
                    minutes = DEFAULT_EXPIRATION_MINUTES_TIME
                )
            )
            logger.info("c=CreateHashUseCase m=execute() s=done identifier=$identifier")
            return hash
        } catch (ex: Exception) {
            logger.error("c=CreateHashUseCase m=execute() s=error-generic identifier=$identifier message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
