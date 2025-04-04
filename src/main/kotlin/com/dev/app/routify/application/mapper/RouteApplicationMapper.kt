package com.dev.app.routify.application.mapper

import com.dev.app.routify.application.models.CreateRouteDTO
import com.dev.app.routify.domain.entity.RouteDomain

fun CreateRouteDTO.toDomain(): RouteDomain {
    return RouteDomain(
        userId = this.userId
    )
}