package com.dev.app.routify.infrastructure.event.models

import com.dev.app.routify.domain.enums.TemplateEmailEnum
import com.dev.app.routify.domain.objects.Parameter
import java.util.UUID

data class ConfirmationCreatingAccountEventDTO(
    val transaction: UUID,
    val email: String,
    val template: TemplateEmailEnum = TemplateEmailEnum.SEND_CONFIRMATION_CREATING_ACCOUNT,
    val parameters: List<Parameter>?
)
