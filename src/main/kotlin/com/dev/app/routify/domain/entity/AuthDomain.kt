package com.dev.app.routify.domain.entity

import java.io.Serializable
import java.util.*

data class AuthDomain(
    val token: String,
    val typeToken: String,
    val expirationTime: String
) : Serializable
