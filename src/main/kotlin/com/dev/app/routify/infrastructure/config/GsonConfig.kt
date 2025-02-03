package com.dev.app.routify.infrastructure.config

import com.dev.app.routify.infrastructure.adapter.LocalDateTimeJsonAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class GsonConfig {

    @Bean
    fun gson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeJsonAdapter())
            .create()
    }
}
