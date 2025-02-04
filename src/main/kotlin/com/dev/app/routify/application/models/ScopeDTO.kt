package com.dev.app.routify.application.models

import com.dev.app.routify.domain.enums.ScopeKeyEnum

data class ScopeDTO(
    val identifier: ScopeKeyEnum,
    val path: String,
    val scopes: String
)
