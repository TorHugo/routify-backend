package com.dev.app.routify.infrastructure.config

import com.dev.app.routify.infrastructure.security.filter.OAuthScopeFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class WebSecurityConfig (
    private val oAuthScopeFilter: OAuthScopeFilter
) {
    companion object {
        private val PUBLIC = arrayOf("/v1/auth/**")
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .cors { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*PUBLIC).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(oAuthScopeFilter, BearerTokenAuthenticationFilter::class.java)
            .build()
    }
}
