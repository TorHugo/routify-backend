package com.dev.app.routify.application.usecase

import com.dev.app.routify.application.mapper.toDomain
import com.dev.app.routify.application.models.CreateUserDTO
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.gateway.UserGateway
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CreateUserUseCase(
    private val userGateway: UserGateway
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    fun execute(dto: CreateUserDTO): UUID {
        logger.info("c=CreateUserUseCase m=execute() s=start: ${dto.email}")
        val existsUser = userGateway.findByEmail(dto.email)
        if (existsUser != null) throw DomainException("user.already.exist")
        val save = userGateway.save(dto.toDomain())
        logger.info("c=CreateUserUseCase m=execute() s=done: ${dto.email}")
        return save.externalId.value
    }
}
