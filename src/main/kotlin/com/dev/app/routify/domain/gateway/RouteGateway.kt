package com.dev.app.routify.domain.gateway

import com.dev.app.routify.domain.entity.RouteDomain

interface RouteGateway {
    fun save(domain: RouteDomain)
    fun findAllUnfinishedRoutes(userId: Long): List<RouteDomain>
}