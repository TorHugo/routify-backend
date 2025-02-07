package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.models.CreateUserDTO
import com.dev.app.routify.application.models.EventDTO
import com.dev.app.routify.domain.enums.EventTypeEnum
import com.dev.app.routify.domain.enums.ScopeKeyEnum
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.domain.gateway.UserGateway
import com.dev.app.routify.domain.objects.Parameter
import com.dev.app.routify.domain.service.EventService
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateUserUseCase(
    private val eventService: EventService,
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
        private const val DEFAULT_SCOPE_KEY: String = "scope-user"

        private const val DEFAULT_KEY_FULL_NAME: String = "name"
        private val EVENT_SEND_CONFIRMATION: EventTypeEnum = EventTypeEnum.EVENT_SEND_CONFIRMATION_CREATING_ACCOUNT
        private val EVENT_USER_SCOPES: EventTypeEnum = EventTypeEnum.EVENT_USER_SCOPES
        private val DEFAULT_SCOPE_VALUE: String = ScopeKeyEnum.DEFAULT_USER_SCOPE.value
    }

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

            eventService.publish(
                EventDTO(
                    eventType = EVENT_SEND_CONFIRMATION,
                    domain = save,
                    parameters = listOf(
                        Parameter(
                            key = DEFAULT_KEY_FULL_NAME,
                            value = save.fullName()
                        )
                    )
                )
            )

            eventService.publish(
                EventDTO(
                    eventType = EVENT_USER_SCOPES,
                    domain = save,
                    parameters = listOf(
                        Parameter(key = DEFAULT_SCOPE_KEY, value = DEFAULT_SCOPE_VALUE)
                    )
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
