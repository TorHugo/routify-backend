package com.dev.app.routify.infrastructure.consumer

import com.dev.app.routify.application.usecase.CleanExpirationEventsUseCase
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CleanExpirationEventsEvent(
    private val cleanExpirationEventsUseCase: CleanExpirationEventsUseCase
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @SqsListener("RoutifyCleanExpirationEventsQueue")
    fun subscribe(message: Any) {
        logger.info("c=CleanExpirationEventsEvent m=subscribe s=Start message=$message")
        cleanExpirationEventsUseCase.execute()
        logger.info("c=CleanExpirationEventsEvent m=subscribe s=Start message=$message")
    }
}
