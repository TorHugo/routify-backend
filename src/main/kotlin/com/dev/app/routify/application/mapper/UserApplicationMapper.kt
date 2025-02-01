package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.CreateUserDTO
import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.objects.Email

fun CreateUserDTO.toDomain(): UserDomain {
    return UserDomain(
        email = Email(this.email),
        password = this.password,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber
    )
}
