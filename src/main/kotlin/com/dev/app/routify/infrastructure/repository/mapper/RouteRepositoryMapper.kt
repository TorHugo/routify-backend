package com.dev.app.routify.infrastructure.repository.mapper

import com.dev.app.routify.domain.entity.RouteDomain
import com.dev.app.routify.domain.objects.StatusRoute
import com.dev.app.routify.domain.objects.UUIDv4
import com.dev.app.routify.infrastructure.repository.entity.RouteEntity

fun RouteDomain.toEntity(): RouteEntity {
    return RouteEntity(
        routeId = this.identifier.value,
        userId = this.userId,
        status = this.status?.value,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun RouteEntity.toDomain(locations: String? = null): RouteDomain {
    return RouteDomain(
        identifier = UUIDv4(this.routeId!!),
        userId = this.userId!!,
        status = StatusRoute(this.status!!),
        locations = locations,
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt
    )
}
