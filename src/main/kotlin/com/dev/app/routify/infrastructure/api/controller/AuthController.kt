package com.dev.app.routify.infrastructure.api.controller

import com.dev.app.routify.domain.service.AuthService
import com.dev.app.routify.infrastructure.api.models.request.AuthLoginDTO
import com.dev.app.routify.infrastructure.api.models.response.BasicLoginResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.DefaultResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.LoginResponseDTO
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(
        @Valid @RequestBody
        dto: AuthLoginDTO
    ): DefaultResponseDTO<BasicLoginResponseDTO> {
        val auth = authService.login(
            username = dto.email,
            password = dto.password
        )
        return DefaultResponseDTO.success(
            BasicLoginResponseDTO(
                basicToken = auth.token,
                tokenType = auth.typeToken,
                expirationDate = auth.expirationTime
            )
        )
    }

    @PostMapping("/access-token")
    @ResponseStatus(HttpStatus.OK)
    fun accessToken(
        @RequestHeader("Authorization") token: String
    ): DefaultResponseDTO<LoginResponseDTO> {
        val auth = authService.accessToken(
            token = token
        )
        return DefaultResponseDTO.success(
            LoginResponseDTO(
                accessToken = auth.token,
                tokenType = auth.typeToken,
                expirationDate = auth.expirationTime
            )
        )
    }

    @PatchMapping("/forgot-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun forgotPassword(
        @RequestHeader email: String
    ) {
        authService.forgotPassword(
            username = email
        )
    }
}
