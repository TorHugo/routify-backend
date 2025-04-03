package com.dev.app.routify.infrastructure.consumer

import com.dev.app.routify.application.usecase.CreateUserScopeUseCase
import com.dev.app.routify.domain.entity.UserDomain
import com.dev.app.routify.domain.event.DomainEvent
import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.infrastructure.consumer.models.EventConsumerDTO
import com.google.gson.Gson
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ConsumerPermissionsEvent(
    private val gson: Gson,
    private val createUserScopeUseCase: CreateUserScopeUseCase
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @SqsListener("RoutifyCustomerPermissionQueue")
    fun subscribe(message: EventConsumerDTO) {
        logger.info("c=ConsumerPermissionsEvent m=subscribe s=Start messageId=${message.messageId} type=${message.type}")
        val event = gson.fromJson(message.message, DomainEvent::class.java)
        if (event.domain == null) {
            throw DomainException("event.domain.is.mandatory.and.not.null")
        }
        if (event.message == null) {
            throw DomainException("event.message.is.mandatory.and.not.null")
        }
        val user = gson.fromJson(event.domain.toString(), UserDomain::class.java)
        val scope = gson.fromJson(event.message.toString(), String::class.java)

        createUserScopeUseCase.execute(
            userId = user.identifier!!,
            scopeId = scope
        )
        logger.info("c=ConsumerPermissionsEvent m=subscribe s=Done messageId=${message.messageId} type=${message.type}")
    }
}
