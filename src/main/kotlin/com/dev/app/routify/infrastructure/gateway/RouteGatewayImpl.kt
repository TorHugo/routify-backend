package com.dev.app.routify.infrastructure.gateway

import com.dev.app.routify.domain.entity.RouteDomain
import com.dev.app.routify.domain.enums.StatusRouteEnum
import com.dev.app.routify.domain.gateway.RouteGateway
import com.dev.app.routify.infrastructure.repository.RouteRepository
import com.dev.app.routify.infrastructure.repository.mapper.toDomain
import com.dev.app.routify.infrastructure.repository.mapper.toEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RouteGatewayImpl(
    private val routeRepository: RouteRepository
) : RouteGateway {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private val DEFAULT_FINISHED_STATUS: String = StatusRouteEnum.FINISHED.value
    }

    override fun save(domain: RouteDomain) {
        logger.info("c=RouteGatewayImpl m=save() s=start userId=${domain.userId}")
        routeRepository.save(domain.toEntity())
        logger.info("c=RouteGatewayImpl m=save() s=done userId=${domain.userId}")
    }

    override fun findAllUnfinishedRoutes(userId: Long): List<RouteDomain> {
        logger.info("c=RouteGatewayImpl m=findAllUnfinishedRoutes() s=start userId=$userId")
        val routes = routeRepository.findAllUnfinishedRoutes(
            userId = userId,
            status = DEFAULT_FINISHED_STATUS
        )
        logger.info("c=RouteGatewayImpl m=findAllUnfinishedRoutes() s=done userId=$userId")
        return routes.map {
            it.toDomain()
        }
    }
}
