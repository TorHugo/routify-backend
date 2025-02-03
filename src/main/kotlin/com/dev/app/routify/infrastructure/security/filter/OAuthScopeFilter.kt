package com.dev.app.routify.infrastructure.security.filter

import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.AccessDeniedException
import com.dev.app.routify.domain.exception.template.AuthenticationException
import com.dev.app.routify.domain.extension.toSafeListOfMaps
import com.dev.app.routify.domain.service.PermissionService
import com.dev.app.routify.infrastructure.api.models.response.AuthErrorResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.DefaultResponseDTO
import com.dev.app.routify.infrastructure.security.JWTAuthToken
import com.google.gson.Gson
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.MessageSource
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class OAuthScopeFilter(
    private val jwtAuthToken: JWTAuthToken,
    private val permissionService: PermissionService,
    private val messageSource: MessageSource,
    private val gson: Gson
) : OncePerRequestFilter() {

    companion object {
        private val PUBLIC = arrayOf("/api/v1/auth/.*")

        private const val DEFAULT_ERROR_UNAUTHORIZED: String = "Unauthorized"
        private const val DEFAULT_ERROR_FORBIDDEN: String = "Forbidden"
        private const val DEFAULT_ERROR_INTERNAL_SERVER_ERROR: String = "Forbidden"
        private const val DEFAULT_ERROR_EMPTY_MESSAGE: String = ""

        private const val DEFAULT_AUTHORIZATION_HEADER: String = "Authorization"
        private const val DEFAULT_AUTHORIZATION_HEADER_VALUE: String = "Bearer "
        private const val DEFAULT_CLAIM_SCOPE: String = "scopes"
        private const val DEFAULT_CLAIM_SCOPE_PATH: String = "path"
        private const val DEFAULT_CLAIM_SCOPE_METHOD: String = "scopes"
        private const val DEFAULT_CLAIM_SCOPE_METHOD_DELIMITERS: String = ", "
        private const val DEFAULT_SUBSTRING_TO_BEARER_TOKEN: Int = 7
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.requestURI
        val method = request.method
        logger.info("c=OAuthScopeFilter m=doFilterInternal() s=start $path $method")

        if (isPublicPath(path)){
            logger.info("c=OAuthScopeFilter m=doFilterInternal() s=info $path is public")
            filterChain.doFilter(request, response)
            return
        }

        try {
            val token = extractToken(request)
                ?: throw AuthenticationException(ErrorMessageEnum.ERROR_AUTHENTICATION_BEARER_TOKEN_IS_NOT_EMPTY.message)

            val claims = jwtAuthToken.getClaims(token)
            val scopes = claims[DEFAULT_CLAIM_SCOPE, List::class.java].toSafeListOfMaps()

            val hasAccess = scopes.any { scope ->
                val scopePath = scope[DEFAULT_CLAIM_SCOPE_PATH] as String
                val scopeMethods = (scope[DEFAULT_CLAIM_SCOPE_METHOD] as String).split(DEFAULT_CLAIM_SCOPE_METHOD_DELIMITERS)
                path.matches(scopePath.toRegex()) &&
                        (scopeMethods.contains(getPermissions(method.toString())))
            }

            if (!hasAccess) {
                throw AccessDeniedException(ErrorMessageEnum.ERROR_AUTHENTICATION_ACCESS_DENIED.message)
            }

        } catch (ex: Exception) {
            when (ex) {
                is AccessDeniedException -> {
                    logger.error("c=OAuthScopeFilter m=doFilterInternal() s=error-access-denied path=$path method=$method message=${ex.message}")
                    handleErrorResponse(
                        response = response,
                        status = HttpServletResponse.SC_FORBIDDEN,
                        type = DEFAULT_ERROR_FORBIDDEN,
                        message = ex.message
                    )
                    return
                }
                is AuthenticationException -> {
                    logger.error("c=OAuthScopeFilter m=doFilterInternal() s=error-authentication-exception path=$path method=$method message=${ex.message}")
                    handleErrorResponse(
                        response = response,
                        status = HttpServletResponse.SC_UNAUTHORIZED,
                        type = DEFAULT_ERROR_UNAUTHORIZED,
                        message = ex.message
                    )
                    return
                }
                is NullPointerException -> {
                    logger.error("c=OAuthScopeFilter m=doFilterInternal() s=error-scopes-be-null path=$path method=$method message=${ex.message}")
                    handleErrorResponse(
                        response = response,
                        status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        type = DEFAULT_ERROR_INTERNAL_SERVER_ERROR,
                        message = ex.message
                    )
                    return
                }
                else -> {
                    logger.error("Cannot set user authentication.")
                    handleErrorResponse(
                        response = response,
                        status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        type = DEFAULT_ERROR_INTERNAL_SERVER_ERROR,
                        message = ex.message
                    )
                    return
                }
            }
        }


        logger.info("c=OAuthScopeFilter m=doFilterInternal() s=before-doFilter path=$path method=$method")
        filterChain.doFilter(request, response)
        logger.info("c=OAuthScopeFilter m=doFilterInternal() s=after-doFilter path=$path method=$method")
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(DEFAULT_AUTHORIZATION_HEADER)
        return if (bearerToken != null && bearerToken.startsWith(DEFAULT_AUTHORIZATION_HEADER_VALUE)) {
            bearerToken.substring(startIndex = DEFAULT_SUBSTRING_TO_BEARER_TOKEN)
        } else {
            null
        }
    }

    private fun isPublicPath(path: String): Boolean {
        return PUBLIC.any { publicPath ->
            path.matches(publicPath.toRegex())
        }
    }

    private fun getPermissions(httpMethod: Any): String {
        return permissionService.getPermission(httpMethod = httpMethod.toString())
    }

    private fun handleErrorResponse(
        response: HttpServletResponse,
        status: Int,
        type: String,
        message: String?
    ) {
        response.contentType = APPLICATION_JSON_VALUE
        response.status = status

        val body = AuthErrorResponseDTO(
            error = type,
            message = messageSource.getMessage(message ?: DEFAULT_ERROR_EMPTY_MESSAGE, null, Locale.getDefault())
        )

        val defaultError = when (status) {
            HttpServletResponse.SC_FORBIDDEN -> DefaultResponseDTO.forbidden(data = body)
            HttpServletResponse.SC_UNAUTHORIZED -> DefaultResponseDTO.unauthorized(data = body)
            else -> DefaultResponseDTO.internalServerError(data = body)
        }

        response.writer.write(gson.toJson(defaultError))
    }
}