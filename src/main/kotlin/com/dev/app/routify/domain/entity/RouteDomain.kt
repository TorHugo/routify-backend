package com.dev.app.routify.domain.entity

import com.dev.app.routify.domain.enums.StatusRouteEnum
import com.dev.app.routify.domain.objects.StatusRoute
import com.dev.app.routify.domain.objects.UUIDv4
import java.io.Serializable
import java.time.LocalDateTime

data class RouteDomain(
    override val identifier: UUIDv4 = UUIDv4.generate(),
    val userId: Long,
    var status: StatusRoute? = null,
    /** To starting this value is null. **/
    val locations: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
) : Serializable, AggregateRoot<UUIDv4?>() {
    fun started() {
        this.status = StatusRoute(StatusRouteEnum.STARTED.value)
    }
}
