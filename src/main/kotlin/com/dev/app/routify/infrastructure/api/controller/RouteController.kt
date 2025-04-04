package com.dev.app.routify.infrastructure.api.controller

import com.dev.app.routify.application.models.CreateRouteDTO
import com.dev.app.routify.application.usecase.CreateRouteUseCase
import com.dev.app.routify.application.usecase.FindUserWithThrowsUseCase
import com.dev.app.routify.infrastructure.api.models.response.DefaultResponseDTO
import com.dev.app.routify.infrastructure.api.models.response.RouteCreatedResponseDTO
import com.dev.app.routify.infrastructure.security.JWTAuthToken
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/routes")
class RouteController(
    private val jwtAuthToken: JWTAuthToken,
    private val findUserWithThrowsUseCase: FindUserWithThrowsUseCase,
    private val createRouteUseCase: CreateRouteUseCase
) {
    @PostMapping("/entrypoint")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestHeader("Authorization") token: String,
        ): DefaultResponseDTO<RouteCreatedResponseDTO> {
        val claims = jwtAuthToken.getClaims(token)
        val user = findUserWithThrowsUseCase.execute(
            email = claims.subject
        )

        val routeId = createRouteUseCase.execute(dto = CreateRouteDTO(
            userId = user.identifier!!
        ))

        return DefaultResponseDTO.created(
            RouteCreatedResponseDTO(routeId = routeId)
        )
    }
}
