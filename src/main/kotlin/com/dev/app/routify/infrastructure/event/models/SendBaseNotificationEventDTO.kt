package com.dev.app.routify.infrastructure.event.models

import com.dev.app.routify.application.models.UserDTO
import com.dev.app.routify.domain.enums.StatusNotificationEnum
import com.dev.app.routify.domain.enums.TemplateEmailEnum
import com.dev.app.routify.domain.enums.TypeNotificationEnum
import com.dev.app.routify.domain.objects.Parameter
import java.util.*

data class SendBaseNotificationEventDTO(
    val transaction: UUID,
    val user: UserDTO,
    val statusNotification: StatusNotificationEnum = StatusNotificationEnum.SENDING,
    val typeNotification: TypeNotificationEnum,
    val template: TemplateEmailEnum,
    val parameters: List<Parameter>
)
