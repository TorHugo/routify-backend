package com.dev.app.routify.infrastructure.config

import com.dev.app.routify.infrastructure.security.filter.OAuthScopeFilter
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val oAuthScopeFilter: OAuthScopeFilter
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(oAuthScopeFilter)
    }
}
