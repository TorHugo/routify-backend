package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.models.CreateRouteDTO
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.RouteGateway
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateRouteUseCase(
    private val routeGateway: RouteGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun execute(dto: CreateRouteDTO): UUID {
        try {
            logger.info("c=CreateRouteUseCase m=execute() s=start userId=${dto.userId}")
            val unfinishedRoutes = routeGateway.findAllUnfinishedRoutes(
                userId = dto.userId
            )

            if (unfinishedRoutes.isNotEmpty()){
                throw DomainException(ErrorMessageEnum.ERROR_ROUTE_USER_ALREADY_UNFINISHED_ROUTES.message)
            }

            val domain = dto.toDomain()
            domain.started()

            routeGateway.save(
                domain = domain
            )
            logger.info("c=CreateRouteUseCase m=execute() s=done email=${dto.userId} routeId=${domain.identifier.value}")
            return domain.identifier.value
        } catch (ex: DomainException) {
            logger.error("c=CreateRouteUseCase m=execute() s=domain-error email=${dto.userId} message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=CreateRouteUseCase m=execute() s=generic-error email=${dto.userId} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
