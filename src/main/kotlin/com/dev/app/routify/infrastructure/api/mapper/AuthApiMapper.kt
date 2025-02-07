package com.dev.app.routify.infrastructure.api.mapper

import com.dev.app.routify.application.models.ConfirmationUserDTO
import com.dev.app.routify.application.models.ResetPasswordDTO
import com.dev.app.routify.infrastructure.api.models.request.ResetPasswordRequestDTO
import com.dev.app.routify.infrastructure.api.models.request.UserConfirmationRequestDTO

fun UserConfirmationRequestDTO.toApplicationDTO(email: String): ConfirmationUserDTO {
    return ConfirmationUserDTO(
        email = email,
        hashcode = this.hash
    )
}

fun ResetPasswordRequestDTO.toApplicationDTO(email: String, password: String): ResetPasswordDTO {
    return ResetPasswordDTO(
        email = email,
        password = password,
        hashcode = this.hash
    )
}
