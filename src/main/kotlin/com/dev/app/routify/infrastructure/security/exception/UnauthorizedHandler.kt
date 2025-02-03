package com.dev.app.routify.infrastructure.security.exception

import com.dev.app.routify.infrastructure.api.models.response.AuthErrorResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.DefaultResponseDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class UnauthorizedHandler(
    private val gson: Gson
) : AuthenticationEntryPoint {
    companion object {
        private const val ERROR_MESSAGE: String = "Unauthorized"
        private const val ERROR_EMPTY_MESSAGE: String = ""
    }

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.contentType = APPLICATION_JSON_VALUE
        response?.status = HttpServletResponse.SC_UNAUTHORIZED
        val body = AuthErrorResponseDTO(
            error = ERROR_MESSAGE,
            message = authException?.message ?: ERROR_EMPTY_MESSAGE
        )


        val defaultError = DefaultResponseDTO.unauthorized(
            data = body
        )

        val mapper = ObjectMapper()
        mapper.writeValue(response?.outputStream, defaultError)
    }
}