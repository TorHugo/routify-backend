package com.dev.app.routify.infrastructure.event.models

import com.dev.app.routify.application.models.UserDTO
import com.dev.app.routify.domain.enums.TemplateEmailEnum
import com.dev.app.routify.domain.objects.Parameter
import java.util.*

data class ConfirmationCreatingAccountEventDTO(
    val transaction: UUID,
    val user: UserDTO,
    val template: TemplateEmailEnum = TemplateEmailEnum.SEND_CONFIRMATION_CREATING_ACCOUNT,
    val parameters: List<Parameter>?
)
