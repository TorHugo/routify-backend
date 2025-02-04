package com.dev.app.routify.application.models

data class UserScopeDTO(
    val user: UserDTO,
    val scopes: List<ScopeDTO>
)
