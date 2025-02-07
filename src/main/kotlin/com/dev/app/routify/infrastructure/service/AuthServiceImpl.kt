package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.models.AuthDTO
import com.dev.app.routify.application.models.EventDTO
import com.dev.app.routify.application.models.ResetPasswordDTO
import com.dev.app.routify.application.usecase.FindUserScopeUseCase
import com.dev.app.routify.domain.enums.EventTypeEnum
import com.dev.app.routify.domain.enums.TypeNotificationEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.AuthenticationException
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.exception.template.JWTException
import com.dev.app.routify.domain.extension.toFormatted
import com.dev.app.routify.domain.gateway.NotificationGateway
import com.dev.app.routify.domain.gateway.TokenGateway
import com.dev.app.routify.domain.gateway.UserGateway
import com.dev.app.routify.domain.objects.Parameter
import com.dev.app.routify.domain.service.AuthService
import com.dev.app.routify.domain.service.EventService
import com.dev.app.routify.infrastructure.security.JWTAuthToken
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import io.jsonwebtoken.security.WeakKeyException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthServiceImpl(
    private val findUserScopeUseCase: FindUserScopeUseCase,
    private val encoder: PasswordEncoder,
    private val tokenGateway: TokenGateway,
    private val notificationGateway: NotificationGateway,
    private val eventService: EventService,
    private val userGateway: UserGateway,
    private val jwtAuthToken: JWTAuthToken
) : AuthService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_KEY_FULL_NAME: String = "name"

        private val EVENT_FORGOT_PASSWORD: EventTypeEnum = EventTypeEnum.EVENT_FORGOT_PASSWORD
        private val DEFAULT_TYPE_NOTIFICATION: TypeNotificationEnum = TypeNotificationEnum.SEND_FORGOT_PASSWORD
    }

    override fun login(
        username: String,
        password: String
    ): AuthDTO {
        try {
            logger.info("c=AuthServiceImpl m=login() s=start email=$username")
            val user = userGateway.findByEmail(email = username) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)
            val isPasswordMatches = encoder.matches(
                password,
                user.password
            )

            if (!user.active) {
                throw DomainException(ErrorMessageEnum.ERROR_AUTHENTICATION_USER_IS_NOT_ACTIVE.message)
            }

            if (!isPasswordMatches) {
                throw AuthenticationException(ErrorMessageEnum.ERROR_AUTHENTICATION_INVALID_ARGUMENTS.message)
            }

            val token = jwtAuthToken.generateToken(
                user = user
            )
            logger.info("c=AuthServiceImpl m=login() s=done email=$username")
            return AuthDTO(
                token = token.token,
                typeToken = token.type,
                expirationTime = token.expirationDate.toFormatted()
            )
        } catch (ex: DomainException) {
            logger.info("c=AuthServiceImpl m=login() s=error-domain email=$username message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: AuthenticationException) {
            logger.info("c=AuthServiceImpl m=login() s=error-authentication email=$username message=${ex.message}")
            throw AuthenticationException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=AuthServiceImpl m=login() s=error-generic email=$username message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }

    override fun accessToken(
        token: String
    ): AuthDTO {
        try {
            logger.info("c=AuthServiceImpl m=accessToken() s=start")
            val claims = jwtAuthToken.getClaims(
                token = token
            )

            val user = userGateway.findByEmail(
                email = claims.subject
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)

            if (!user.active) {
                throw DomainException(ErrorMessageEnum.ERROR_USER_IS_NOT_ACTIVE.message)
            }

            if (!user.confirmed) {
                throw DomainException(ErrorMessageEnum.ERROR_USER_IS_NOT_CONFIRMED.message)
            }

            val userScopes = findUserScopeUseCase.execute(
                dto = user.toApplicationDTO()
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_AUTHENTICATION_SCOPES_NOT_FOUND.message)

            val jwtToken = jwtAuthToken.generateTokenWithScopes(
                scopes = userScopes
            )

            logger.info("c=AuthServiceImpl m=accessToken() s=done")
            return AuthDTO(
                token = jwtToken.token,
                typeToken = jwtToken.type,
                expirationTime = jwtToken.expirationDate.toFormatted()
            )
        } catch (ex: Exception) {
            when (ex) {
                is DomainException -> {
                    logger.info("c=AuthServiceImpl m=accessToken() s=error-domain message=${ex.message}")
                    throw DomainException(ex.message)
                }
                is AuthenticationException -> {
                    logger.info("c=AuthServiceImpl m=accessToken() s=error-authentication message=${ex.message}")
                    throw JWTException(ex.message)
                }
                is WeakKeyException, is SignatureException -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-invalid-signature message=${ex.message}")
                    throw JWTException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
                is MalformedJwtException -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-invalid-jwt-token message=${ex.message}")
                    throw JWTException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
                is ExpiredJwtException -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-expired-jwt message=${ex.message}")
                    throw JWTException(ErrorMessageEnum.ERROR_JWT_IS_EXPIRED.message)
                }
                is UnsupportedJwtException -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-token-is-unsupported message=${ex.message}")
                    throw JWTException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
                is IllegalArgumentException -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-claims-empty message=${ex.message}")
                    throw JWTException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
                else -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-generic message=${ex.message}")
                    throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
            }
        }
    }

    override fun forgotPassword(
        username: String
    ) {
        try {
            logger.info("c=AuthServiceImpl m=forgotPassword() s=start email=$username")
            val user = userGateway.findByEmail(
                email = username
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)

            eventService.publish(
                EventDTO(
                    eventType = EVENT_FORGOT_PASSWORD,
                    domain = user,
                    parameters = listOf(
                        Parameter(
                            key = DEFAULT_KEY_FULL_NAME,
                            value = user.fullName()
                        )
                    )
                )
            )

            logger.info("c=AuthServiceImpl m=forgotPassword() s=done email=$username")
        } catch (ex: DomainException) {
            logger.info("c=AuthServiceImpl m=forgotPassword() s=error-domain email=$username message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=AuthServiceImpl m=forgotPassword() s=error-generic email=$username message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }

    override fun resetPassword(dto: ResetPasswordDTO) {
        try {
            logger.info("c=AuthServiceImpl m=resetPassword() s=start email=${dto.email}")
            val user = userGateway.findByEmail(
                email = dto.email
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)

            val token = tokenGateway.findByUserIdAndHashToken(
                userId = user.identifier!!,
                hashcode = dto.hashcode
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_TOKEN_NOT_FOUND.message)

            val notification = notificationGateway.findByUserIdAndNotificationType(
                userId = user.identifier,
                type = DEFAULT_TYPE_NOTIFICATION.value
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_NOTIFICATION_EMAIL_NOT_FOUND.message)

            val currentDate = LocalDateTime.now()
            if (token.used!!) {
                throw DomainException(ErrorMessageEnum.ERROR_FORGOT_PASSWORD_TOKEN_HAS_BEEN_USED.message)
            }

            if (!user.confirmed) {
                throw DomainException(ErrorMessageEnum.ERROR_USER_IS_NOT_CONFIRMED.message)
            }

            if (token.expiration.value.isBefore(currentDate)) {
                notificationGateway.delete(notification)
                tokenGateway.delete(token)
                throw DomainException(ErrorMessageEnum.ERROR_FORGOT_PASSWORD_IS_EXPIRED.message)
            }

            user.resetPassword(
                newPassword = dto.password
            )
            token.confirmation()
            notification.confirmation()

            userGateway.save(
                domain = user
            )
            tokenGateway.save(
                domain = token
            )
            notificationGateway.save(
                domain = notification
            )
            logger.info("c=AuthServiceImpl m=resetPassword() s=done email=${dto.email}")
        } catch (ex: DomainException) {
            logger.info("c=AuthServiceImpl m=resetPassword() s=error-domain email=${dto.email} message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=AuthServiceImpl m=resetPassword() s=error-generic email=${dto.email} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
