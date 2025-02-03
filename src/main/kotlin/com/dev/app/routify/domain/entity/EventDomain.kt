package com.dev.app.routify.domain.entity

import com.dev.app.routify.domain.enums.EventTypeEnum
import com.dev.app.routify.domain.objects.Parameter
import com.dev.app.routify.domain.objects.UUIDv4
import java.io.Serializable

data class EventDomain(
    override val identifier: UUIDv4 = UUIDv4.generate(),
    val eventType: EventTypeEnum,
    val domain: Any?,
    val parameters: List<Parameter>?
) : Serializable, AggregateRoot<UUIDv4>()
