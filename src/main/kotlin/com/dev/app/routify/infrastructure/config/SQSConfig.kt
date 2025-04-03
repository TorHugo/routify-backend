package com.dev.app.routify.infrastructure.config

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.time.Duration

@Configuration
class SQSConfig(
    @Value("\${spring.aws.region}")
    private var region: String,
    @Value("\${spring.aws.sqs.max-number-messages}")
    private var maxNumberReceiveOfMessage: Int,
    @Value("\${spring.aws.sqs.visibility-timeout}")
    private var visibilityTimeout: Int,
    @Value("\${spring.aws.sqs.await-timeout}")
    private var awaitTimeout: Int
) {
    @Bean
    fun sqsAsyncClient(): SqsAsyncClient {
        return SqsAsyncClient.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()
    }

    @Bean
    fun sqsTemplate(sqsAsyncClient: SqsAsyncClient): SqsTemplate {
        return SqsTemplate.builder()
            .sqsAsyncClient(sqsAsyncClient)
            .build()
    }

    @Bean
    fun defaultSqsListenerContainerFactory(sqsAsyncClient: SqsAsyncClient): SqsMessageListenerContainerFactory<Any> {
        return SqsMessageListenerContainerFactory.builder<Any>()
            .sqsAsyncClient(sqsAsyncClient)
            .configure {
                it.maxMessagesPerPoll(maxNumberReceiveOfMessage)
                    .messageVisibility(Duration.ofSeconds(visibilityTimeout.toLong()))
                    .pollTimeout(Duration.ofSeconds(awaitTimeout.toLong()))
            }
            .build()
    }
}
