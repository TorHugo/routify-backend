package com.dev.app.routify.infrastructure.api.mapper

import com.dev.app.routify.application.models.CreateUserDTO
import com.dev.app.routify.application.models.UpdateUserDTO
import com.dev.app.routify.application.models.UpdateUserPasswordDTO
import com.dev.app.routify.infrastructure.api.models.request.UserRequestDTO
import com.dev.app.routify.infrastructure.api.models.request.UserUpdatePasswordRequestDTO
import com.dev.app.routify.infrastructure.api.models.request.UserUpdateRequestDTO

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
