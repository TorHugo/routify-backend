package com.dev.app.routify.infrastructure.security

import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.entity.UserScopeDomain
import com.dev.app.routify.domain.extension.expirationDate
import com.dev.app.routify.infrastructure.security.models.BasicTokenDTO
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.builder
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Service
class JWTAuthToken {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object{
        private const val DEFAULT_EMPTY_VALUE: String = ""
        private const val DEFAULT_BEARER_TOKEN: String = "Bearer"
        private const val DEFAULT_BEARER_WITH_SPACE_TOKEN: String = "Bearer "
        private const val DEFAULT_CLAIM_EMAIL: String = "email"
        private const val DEFAULT_CLAIM_USER_EXTERNAL_ID: String = "user_external_id"
        private const val DEFAULT_CLAIM_SCOPES: String = "scopes"
        private const val DEFAULT_EXPIRATION_SCOPE_TOKEN_TIME: Int = 180
    }

    @Value("\${spring.security.security-key}")
    private lateinit var secret: String

    fun generateToken(user: UserDomain): BasicTokenDTO {
        val currentDate = Date()
        val expirationDate = currentDate.expirationDate()
        val basicToken = builder()
            .subject(user.email.value)
            .claim(DEFAULT_CLAIM_EMAIL, user.email.value)
            .claim(DEFAULT_CLAIM_USER_EXTERNAL_ID, user.externalId.value)
            .issuedAt(currentDate)
            .expiration(expirationDate)
            .signWith(getSigningKey(secret))
            .compact()

        return BasicTokenDTO(
            token = basicToken,
            type = DEFAULT_BEARER_TOKEN,
            expirationDate = expirationDate
        )
    }

    fun generateTokenWithScopes(scopes: UserScopeDomain): BasicTokenDTO {
        val user = scopes.user
        val currentDate = Date()
        val expirationDate = currentDate.expirationDate(DEFAULT_EXPIRATION_SCOPE_TOKEN_TIME)

        val basicToken = builder()
            .subject(user.email.value)
            .claim(DEFAULT_CLAIM_EMAIL, user.email.value)
            .claim(DEFAULT_CLAIM_USER_EXTERNAL_ID, user.externalId.value)
            .claim(DEFAULT_CLAIM_SCOPES, scopes.scopes)
            .issuedAt(currentDate)
            .expiration(expirationDate)
            .signWith(getSigningKey(secret))
            .compact()

        return BasicTokenDTO(
            token = basicToken,
            type = DEFAULT_BEARER_TOKEN,
            expirationDate = expirationDate
        )
    }

    fun getClaims(token: String): Claims {
        logger.info("c=JWTAuthToken m=getClaims() s=start")
        val tokenNoBearer = token.replace(
            oldValue = DEFAULT_BEARER_WITH_SPACE_TOKEN,
            newValue = DEFAULT_EMPTY_VALUE
        )

        val claims = Jwts.parser()
            .verifyWith(getSigningKey(secret))
            .build()
            .parseSignedClaims(tokenNoBearer)

        logger.info("c=JWTAuthToken m=getClaims() s=done")
        return claims.payload
    }

    private fun getSigningKey(secret: String): SecretKey =
        Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

}