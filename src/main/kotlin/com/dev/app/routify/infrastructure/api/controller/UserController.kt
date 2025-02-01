package com.dev.app.routify.infrastructure.api.controller

import com.dev.app.routify.application.usecase.CreateUserUseCase
import com.dev.app.routify.infrastructure.api.mapper.toApplicationDTO
import com.dev.app.routify.infrastructure.api.models.request.UserRequestDTO
import com.dev.app.routify.infrastructure.api.models.response.DefaultResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.UserResponseDTO
import jakarta.validation.Valid
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val encoder: PasswordEncoder,
    private val createUser: CreateUserUseCase
) {

    @PostMapping
    fun create(
        @Valid @RequestBody
        dto: UserRequestDTO
    ): DefaultResponseDTO<UserResponseDTO> {
        val encryptedPassword = encoder.encode(dto.password)
        val externalId = createUser.execute(dto.toApplicationDTO(encryptedPassword))
        return DefaultResponseDTO.created(UserResponseDTO(externalId))
    }
}
