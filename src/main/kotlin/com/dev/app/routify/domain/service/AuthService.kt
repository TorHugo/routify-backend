package com.dev.app.routify.domain.service

import com.dev.app.routify.application.models.AuthDTO

interface AuthService {
    fun login(
        username: String,
        password: String
    ): AuthDTO

    fun accessToken(
        token: String
    ): AuthDTO

    fun forgotPassword(
        username: String
    )
}
