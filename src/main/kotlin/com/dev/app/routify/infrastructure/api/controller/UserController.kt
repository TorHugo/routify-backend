package com.dev.app.routify.infrastructure.api.controller

import com.dev.app.routify.application.usecase.ConfirmationUserUseCase
import com.dev.app.routify.application.usecase.CreateUserUseCase
import com.dev.app.routify.infrastructure.api.mapper.toApplicationDTO
import com.dev.app.routify.infrastructure.api.models.request.UserConfirmationDTO
import com.dev.app.routify.infrastructure.api.models.request.UserRequestDTO
import com.dev.app.routify.infrastructure.api.models.response.DefaultResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.UserResponseDTO
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val encoder: PasswordEncoder,
    private val createUser: CreateUserUseCase,
    private val confirmationUser: ConfirmationUserUseCase
) {
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody
        dto: UserRequestDTO
    ): DefaultResponseDTO<UserResponseDTO> {
        val encryptedPassword = encoder.encode(dto.password)
        val externalId = createUser.execute(dto.toApplicationDTO(encryptedPassword))
        return DefaultResponseDTO.created(UserResponseDTO(externalId))
    }

    @PatchMapping("/confirm-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun confirm(
        @Valid @RequestBody
        dto: UserConfirmationDTO
    ) {
        confirmationUser.execute(dto.toApplicationDTO())
    }
}
