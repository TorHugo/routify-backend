package com.dev.app.routify.domain.service

interface PermissionService {
    fun getPermission(httpMethod: String): String
}
