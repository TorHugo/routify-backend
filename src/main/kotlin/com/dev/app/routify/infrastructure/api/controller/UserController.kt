package com.dev.app.routify.infrastructure.api.controller

import com.dev.app.routify.application.usecase.CreateUserUseCase
import com.dev.app.routify.application.usecase.FindUserWithThrowsUseCase
import com.dev.app.routify.application.usecase.UpdateUserPasswordUseCase
import com.dev.app.routify.application.usecase.UpdateUserUseCase
import com.dev.app.routify.infrastructure.api.mapper.toApplicationDTO
import com.dev.app.routify.infrastructure.api.mapper.toResponseDTO
import com.dev.app.routify.infrastructure.api.models.request.UserRequestDTO
import com.dev.app.routify.infrastructure.api.models.request.UserUpdatePasswordRequestDTO
import com.dev.app.routify.infrastructure.api.models.request.UserUpdateRequestDTO
import com.dev.app.routify.infrastructure.api.models.response.DefaultResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.UserCreatedResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.UserResponseDTO
import com.dev.app.routify.infrastructure.security.JWTAuthToken
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val encoder: PasswordEncoder,
    private val jwtAuthToken: JWTAuthToken,
    private val createUser: CreateUserUseCase,
    private val findUserWithThrowsUseCase: FindUserWithThrowsUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase
) {
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody
        dto: UserRequestDTO
    ): DefaultResponseDTO<UserCreatedResponseDTO> {
        val encryptedPassword = encoder.encode(dto.password)
        val externalId = createUser.execute(dto.toApplicationDTO(encryptedPassword))
        return DefaultResponseDTO.created(UserCreatedResponseDTO(externalId))
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(
        @RequestHeader("Authorization") token: String,
        @RequestBody dto: UserUpdateRequestDTO
    ) {
        val claims = jwtAuthToken.getClaims(token)
        updateUserUseCase.execute(
            currentEmail = claims.subject,
            dto = dto.toApplicationDTO()
        )
    }

    @PatchMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatePassword(
        @RequestHeader("Authorization") token: String,
        @RequestBody dto: UserUpdatePasswordRequestDTO
    ) {
        val claims = jwtAuthToken.getClaims(token)
        val user = findUserWithThrowsUseCase.execute(claims.subject)
        val encryptedPassword = encoder.encode(dto.newPassword)
        val matches = encoder.matches(dto.currentPassword, user.password!!)
        updateUserPasswordUseCase.execute(
            user = user,
            dto = dto.toApplicationDTO(
                matches = matches,
                encryptedPassword = encryptedPassword
            )
        )
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    fun findUser(
        @RequestHeader("Authorization") token: String
    ): DefaultResponseDTO<UserResponseDTO> {
        val claims = jwtAuthToken.getClaims(token)
        val user = findUserWithThrowsUseCase.execute(claims.subject)
        return DefaultResponseDTO.success(
            data = user.toResponseDTO()
        )
    }
}
