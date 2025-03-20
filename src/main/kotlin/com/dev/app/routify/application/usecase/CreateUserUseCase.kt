package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.models.CreateUserDTO
import com.dev.app.routify.domain.enums.DomainEventTypeEnum
import com.dev.app.routify.domain.enums.ScopeKeyEnum
import com.dev.app.routify.domain.enums.SubTypeEventEnum
import com.dev.app.routify.domain.event.DomainEvent
import com.dev.app.routify.domain.event.DomainEventProducer
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserGateway
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateUserUseCase(
    private val domainEventProducer: DomainEventProducer,
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun execute(dto: CreateUserDTO): UUID {
        try {
            logger.info("c=CreateUserUseCase m=execute() s=start email=${dto.email}")
            val existsUser = userGateway.findByEmail(
                email = dto.email
            )

            if (existsUser != null) {
                throw DomainException(ErrorMessageEnum.ERROR_USER_ALREADY_EXISTS.message)
            }

            val save = userGateway.save(
                domain = dto.toDomain()
            )

            domainEventProducer.publish(
                domainEventType = DomainEventTypeEnum.NOTIFICATION_EVENT,
                message = DomainEvent(
                    domain = save,
                    subType = SubTypeEventEnum.SUB_TYPE_CONFIRMATION_CUSTOMER
                )
            )

            domainEventProducer.publish(
                domainEventType = DomainEventTypeEnum.CUSTOMER_PERMISSIONS_EVENT,
                message = DomainEvent(
                    domain = save,
                    message = ScopeKeyEnum.DEFAULT_USER_SCOPE.value
                )
            )
            logger.info("c=CreateUserUseCase m=execute() s=done email=${dto.email}")
            return save.externalId.value
        } catch (ex: DomainException) {
            logger.error("c=CreateUserUseCase m=execute() s=domain-error email=${dto.email} message=${ex.message}")
            throw DomainException(ex.message)
        } catch (ex: Exception) {
            logger.error("c=CreateUserUseCase m=execute() s=generic-error email=${dto.email} message=${ex.message}")
            throw GenericException(ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
        }
    }
}
