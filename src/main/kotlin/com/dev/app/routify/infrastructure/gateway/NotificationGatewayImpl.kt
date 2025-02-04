package com.dev.app.routify.infrastructure.gateway

import com.dev.app.routify.domain.entity.NotificationDomain
import com.dev.app.routify.domain.gateway.NotificationGateway
import com.dev.app.routify.infrastructure.repository.NotificationRepository
import com.dev.app.routify.infrastructure.repository.mapper.toDomain
import com.dev.app.routify.infrastructure.repository.mapper.toEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class NotificationGatewayImpl(
    private val notificationRepository: NotificationRepository
) : NotificationGateway {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    override fun save(domain: NotificationDomain) {
        logger.info("c=NotificationGatewayImpl m=save() s=start identifier=${domain.identifier}")
        notificationRepository.save(domain.toEntity())
        logger.info("c=NotificationGatewayImpl m=save() s=done identifier=${domain.identifier}")
    }

    override fun findByUserIdAndNotificationType(
        userId: Long,
        type: String
    ): NotificationDomain? {
        logger.info("c=NotificationGatewayImpl m=findByEmailAndNotificationType() s=start userId=$userId")
        val entity = notificationRepository.findByUserIdAndType(
            userId = userId,
            type = type
        )
        logger.info("c=NotificationGatewayImpl m=findByEmailAndNotificationType() s=done userId=$userId")
        return entity?.toDomain()
    }
}
