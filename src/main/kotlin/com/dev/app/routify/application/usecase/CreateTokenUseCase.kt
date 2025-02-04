package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.models.TokenDTO
import com.dev.app.routify.application.models.UserDTO
import com.dev.app.routify.domain.entity.TokenDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.TokenGateway
import com.dev.app.routify.domain.objects.ExpirationDate
import com.dev.app.routify.domain.objects.GenericHash
import com.dev.app.routify.domain.objects.TypeToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CreateTokenUseCase(
    private val tokenGateway: TokenGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_EXPIRATION_MINUTES_TIME: Long = 15L
    }

    fun execute(user: UserDTO, tokenType: String): TokenDTO {
        try {
            logger.info("c=CreateHashUseCase m=execute() s=start user=${user.email}")
            val userDomain = user.toDomain()
            val tokenHash = TokenDomain(
                userId = userDomain.identifier!!,
                tokenHash = GenericHash.generate(),
                tokenType = TypeToken(tokenType),
                expiration = ExpirationDate.generate(
                    minutes = DEFAULT_EXPIRATION_MINUTES_TIME
                )
            )
            tokenGateway.save(tokenHash)
            logger.info("c=CreateHashUseCase m=execute() s=done user=${user.email}")
            return tokenHash.toApplicationDTO()
        } catch (ex: Exception) {
            logger.error("c=CreateHashUseCase m=execute() s=error-generic user=${user.email} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
