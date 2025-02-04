package com.dev.app.routify.infrastructure.security.interceptor

import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.AccessDeniedException
import com.dev.app.routify.domain.exception.template.AuthenticationException
import com.dev.app.routify.domain.exception.template.InternalServerException
import com.dev.app.routify.domain.extension.toSafeListOfMaps
import com.dev.app.routify.domain.service.PermissionService
import com.dev.app.routify.infrastructure.security.JWTAuthToken
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class OAuthScopeInterceptor(
    private val jwtAuthToken: JWTAuthToken,
    private val permissionService: PermissionService
) : HandlerInterceptor {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private val PUBLIC = arrayOf("/api/v1/auth/.*", "/api/error")

        private const val DEFAULT_AUTHORIZATION_HEADER = "Authorization"
        private const val DEFAULT_AUTHORIZATION_HEADER_VALUE = "Bearer "
        private const val DEFAULT_CLAIM_SCOPE: String = "scopes"

        private const val DEFAULT_CLAIM_SCOPE_PATH: String = "path"
        private const val DEFAULT_CLAIM_SCOPE_METHOD: String = "scopes"
        private const val DEFAULT_CLAIM_SCOPE_METHOD_DELIMITERS: String = ", "
    }

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val path = request.requestURI
        val method = request.method

        try {
            logger.info("c=OAuthScopeInterceptor m=preHandle() s=start path=$path method=$method")

            if (isPublicPath(path)) {
                return true
            }

            val token = extractToken(request)
                ?: throw AuthenticationException(ErrorMessageEnum.ERROR_AUTHENTICATION_BEARER_TOKEN_IS_NOT_EMPTY.message)

            val claims = jwtAuthToken.getClaims(token)
            val scopes = claims[DEFAULT_CLAIM_SCOPE, List::class.java].toSafeListOfMaps()

            val hasAccess = checkPermissions(scopes, path, method)
            if (!hasAccess) {
                throw AccessDeniedException(ErrorMessageEnum.ERROR_AUTHENTICATION_ACCESS_DENIED.message)
            }

            logger.info("c=OAuthScopeInterceptor m=preHandle() s=done path=$path method=$method")
            return true
        } catch (ex: AccessDeniedException) {
            logger.error("c=OAuthScopeFilter m=doFilterInternal() s=error-access-denied path=$path method=$method message=${ex.message}")
            throw AccessDeniedException(ex.message)
        } catch (ex: AuthenticationException) {
            logger.error("c=OAuthScopeFilter m=doFilterInternal() s=error-authentication-exception path=$path method=$method message=${ex.message}")
            throw AuthenticationException(ex.message)
        } catch (ex: NullPointerException) {
            logger.error("c=OAuthScopeFilter m=doFilterInternal() s=error-scopes-be-null path=$path method=$method message=${ex.message}")
            throw InternalServerException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        } catch (ex: Exception) {
            logger.error("c=OAuthScopeFilter m=doFilterInternal() s=error-generic path=$path method=$method message=${ex.message}")
            throw InternalServerException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(DEFAULT_AUTHORIZATION_HEADER)
        return if (bearerToken != null && bearerToken.startsWith(DEFAULT_AUTHORIZATION_HEADER_VALUE)) {
            bearerToken.substring(DEFAULT_AUTHORIZATION_HEADER_VALUE.length)
        } else {
            null
        }
    }

    private fun isPublicPath(path: String): Boolean {
        return PUBLIC.any { publicPath ->
            path.matches(publicPath.toRegex())
        }
    }

    private fun checkPermissions(
        scopes: List<Map<String, Any>>,
        path: String,
        method: String
    ): Boolean {
        return scopes.any { scope ->
            val scopePath = scope[DEFAULT_CLAIM_SCOPE_PATH] as String
            val scopeMethods = (scope[DEFAULT_CLAIM_SCOPE_METHOD] as String).split(DEFAULT_CLAIM_SCOPE_METHOD_DELIMITERS)
            path.matches(scopePath.toRegex()) && scopeMethods.contains(this.getPermissions(method))
        }
    }

    private fun getPermissions(httpMethod: Any): String {
        return permissionService.getPermission(httpMethod = httpMethod.toString())
    }
}
