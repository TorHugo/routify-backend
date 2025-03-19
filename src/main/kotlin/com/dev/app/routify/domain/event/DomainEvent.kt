package com.dev.app.routify.domain.event

import com.dev.app.routify.domain.enums.SubTypeEventEnum
import com.dev.app.routify.domain.objects.UUIDv4
import java.time.LocalDateTime

data class DomainEvent(
    val identifier: UUIDv4 = UUIDv4.generate(),
    /** This object is equals json. **/
    var message: Any? = null,
    /** This object is equals json. **/
    var domain: Any? = null,
    val subType: SubTypeEventEnum? = null,
    val occurredOn: LocalDateTime = LocalDateTime.now()
)
