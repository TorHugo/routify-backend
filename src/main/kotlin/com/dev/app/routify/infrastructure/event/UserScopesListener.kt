package com.dev.app.routify.infrastructure.event

import com.dev.app.routify.application.usecase.CreateUserScopeUseCase
import com.dev.app.routify.domain.exception.enums.ErrorMessageEnum
import com.dev.app.routify.domain.exception.template.GenericException
import com.dev.app.routify.infrastructure.event.models.UserScopesEventDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserScopesListener(
    private val createUserScopeUseCase: CreateUserScopeUseCase
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    companion object {
    }

    @EventListener
    fun onUserScope(dto: UserScopesEventDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                logger.info("c=UserScopesListener m=onUserScope() s=start transactional=${dto.transaction} userId=${dto.userId}")
                createUserScopeUseCase.execute(
                    userId = dto.userId,
                    scopeId = dto.scopeKey
                )
                logger.info("c=UserScopesListener m=onUserScope() s=done transactional=${dto.transaction} userId=${dto.userId}")
            } catch (ex: Exception) {
                logger.info("c=UserScopesListener m=onUserScope() s=error transactional=${dto.transaction} userId=${dto.userId} message=${ex.message}")
                throw GenericException(ex.message ?: ErrorMessageEnum.INTERNAL_SERVER_ERROR.message)
            }
        }
    }
}
