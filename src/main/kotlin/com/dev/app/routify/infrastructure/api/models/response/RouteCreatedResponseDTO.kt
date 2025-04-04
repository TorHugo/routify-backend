package com.dev.app.routify.infrastructure.api.models.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class RouteCreatedResponseDTO(
    @JsonProperty("route_id")
    val routeId: UUID
)
