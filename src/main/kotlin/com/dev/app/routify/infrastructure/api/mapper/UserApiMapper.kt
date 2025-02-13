package com.dev.app.routify.infrastructure.api.mapper

import com.dev.app.routify.application.models.CreateUserDTO
import com.dev.app.routify.application.models.UpdateUserDTO
import com.dev.app.routify.application.models.UpdateUserPasswordDTO
import com.dev.app.routify.application.models.UserDTO
import com.dev.app.routify.domain.extension.toFormatted
import com.dev.app.routify.infrastructure.api.models.request.UserRequestDTO
import com.dev.app.routify.infrastructure.api.models.request.UserUpdatePasswordRequestDTO
import com.dev.app.routify.infrastructure.api.models.request.UserUpdateRequestDTO
import com.dev.app.routify.infrastructure.api.models.response.UserResponseDTO

fun UserRequestDTO.toApplicationDTO(encryptedPassword: String): CreateUserDTO {
    return CreateUserDTO(
        email = this.email,
        password = encryptedPassword,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber
    )
}

fun UserUpdateRequestDTO.toApplicationDTO(): UpdateUserDTO {
    return UpdateUserDTO(
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber
    )
}

fun UserUpdatePasswordRequestDTO.toApplicationDTO(
    matches: Boolean,
    encryptedPassword: String
): UpdateUserPasswordDTO {
    return UpdateUserPasswordDTO(
        matches = matches,
        oldPassword = this.currentPassword,
        newPassword = encryptedPassword
    )
}

fun UserDTO.toResponseDTO(): UserResponseDTO {
    return UserResponseDTO(
        externalId = this.externalId,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        active = this.active,
        confirmed = this.confirmed,
        createdAt = this.createdAt?.toFormatted(),
        updatedAt = this.updatedAt?.toFormatted()
    )
}
