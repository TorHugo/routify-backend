package com.dev.app.routify.infrastructure.config

import com.dev.app.routify.infrastructure.security.interceptor.OAuthScopeInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val oAuthScopeInterceptor: OAuthScopeInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(oAuthScopeInterceptor)
    }
}
