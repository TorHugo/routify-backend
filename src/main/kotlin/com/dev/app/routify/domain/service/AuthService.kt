package com.dev.app.routify.domain.service

import com.dev.app.routify.domain.entity.AuthDomain

interface AuthService {
    fun login(
        username: String,
        password: String
    ): AuthDomain

    fun accessToken(
        token: String
    ): AuthDomain
}
