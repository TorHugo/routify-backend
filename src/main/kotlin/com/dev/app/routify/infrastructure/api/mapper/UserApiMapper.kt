package com.dev.app.routify.infrastructure.api.mapper

import com.dev.app.routify.application.models.ConfirmationUserDTO
import com.dev.app.routify.application.models.CreateUserDTO
import com.dev.app.routify.infrastructure.api.models.request.UserConfirmationDTO
import com.dev.app.routify.infrastructure.api.models.request.UserRequestDTO

fun UserRequestDTO.toApplicationDTO(encryptedPassword: String): CreateUserDTO {
    return CreateUserDTO(
        email = this.email,
        password = encryptedPassword,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber
    )
}

fun UserConfirmationDTO.toApplicationDTO(): ConfirmationUserDTO {
    return ConfirmationUserDTO(
        email = this.email,
        hashcode = this.hashcode
    )
}
