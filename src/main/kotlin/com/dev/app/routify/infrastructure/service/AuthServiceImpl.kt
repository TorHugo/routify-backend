package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.application.mapper.toApplicationDTO
import com.dev.app.routify.application.usecase.FindUserScopeUseCase
import com.dev.app.routify.domain.entity.AuthDomain
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.AuthenticationException
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.exception.template.JWTException
import com.dev.app.routify.domain.extension.toFormatted
import com.dev.app.routify.domain.gateway.UserGateway
import com.dev.app.routify.domain.service.AuthService
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

@Service
class AuthServiceImpl(
    private val findUserScopeUseCase: FindUserScopeUseCase,
    private val encoder: PasswordEncoder,
    private val jwtAuthToken: JWTAuthToken,
    private val userGateway: UserGateway
) : AuthService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun login(
        username: String,
        password: String
    ): AuthDomain {
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
            return AuthDomain(
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
    ): AuthDomain {
        try {
            logger.info("c=AuthServiceImpl m=accessToken() s=start")
            val claims = jwtAuthToken.getClaims(
                token = token
            )

            val user = userGateway.findByEmail(
                email = claims.subject
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_USER_NOT_FOUND.message)

            val userScopes = findUserScopeUseCase.execute(
                dto = user.toApplicationDTO()!!
            ) ?: throw DomainException(ErrorMessageEnum.ERROR_AUTHENTICATION_SCOPES_NOT_FOUND.message)

            val jwtToken = jwtAuthToken.generateTokenWithScopes(
                scopes = userScopes
            )

            logger.info("c=AuthServiceImpl m=accessToken() s=done")
            return AuthDomain(
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
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-invalid-signature: ${ex.message}")
                    throw JWTException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
                is MalformedJwtException -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-invalid-jwt-token: ${ex.message}")
                    throw JWTException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
                is ExpiredJwtException -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-expired-jwt: ${ex.message}")
                    throw JWTException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
                is UnsupportedJwtException -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-token-is-unsupported: ${ex.message}")
                    throw JWTException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
                is IllegalArgumentException -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-claims-empty: ${ex.message}")
                    throw JWTException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
                else -> {
                    logger.error("c=AuthServiceImpl m=accessToken() s=error-generic message=${ex.message}")
                    throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
                }
            }
        }
    }
}
