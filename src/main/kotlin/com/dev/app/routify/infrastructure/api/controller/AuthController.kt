package com.dev.app.routify.infrastructure.api.controller

import com.dev.app.routify.application.usecase.ConfirmationUserUseCase
import com.dev.app.routify.application.usecase.ResendConfirmationEmailUseCase
import com.dev.app.routify.domain.service.AuthService
import com.dev.app.routify.infrastructure.api.mapper.toApplicationDTO
import com.dev.app.routify.infrastructure.api.models.request.AuthLoginRequestDTO
import com.dev.app.routify.infrastructure.api.models.request.ResetPasswordRequestDTO
import com.dev.app.routify.infrastructure.api.models.request.UserConfirmationRequestDTO
import com.dev.app.routify.infrastructure.api.models.response.BasicLoginResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.DefaultResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.LoginResponseDTO
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
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
    private val authService: AuthService,
    private val confirmationUser: ConfirmationUserUseCase,
    private val resendConfirmationEmail: ResendConfirmationEmailUseCase,
    private val encoder: PasswordEncoder
) {
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(
        @Valid @RequestBody
        dto: AuthLoginRequestDTO
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

    @PatchMapping("/resend-confirm-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun resendConfirm(
        @RequestHeader email: String
    ) {
        resendConfirmationEmail.execute(
            email = email
        )
    }

    @PatchMapping("/confirm-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun confirm(
        @RequestHeader email: String,
        @Valid @RequestBody
        dto: UserConfirmationRequestDTO
    ) {
        confirmationUser.execute(
            dto.toApplicationDTO(
                email = email
            )
        )
    }

    @PatchMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun resetPassword(
        @RequestHeader email: String,
        @Valid @RequestBody
        dto: ResetPasswordRequestDTO
    ) {
        val encrypted = encoder.encode(dto.newPassword)
        authService.resetPassword(
            dto.toApplicationDTO(
                email = email,
                password = encrypted
            )
        )
    }
}
