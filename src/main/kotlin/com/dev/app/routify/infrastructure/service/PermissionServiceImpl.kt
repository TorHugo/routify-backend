package com.dev.app.routify.infrastructure.service

import com.dev.app.routify.domain.enums.HttpMethodEnum
import com.dev.app.routify.domain.enums.ScopeEnum
import com.dev.app.routify.domain.service.PermissionService
import org.springframework.stereotype.Component

@Component
class PermissionServiceImpl : PermissionService {
    override fun getPermission(httpMethod: String): String {
        return when (httpMethod) {
            HttpMethodEnum.GET.name -> return ScopeEnum.READ.name
            HttpMethodEnum.PUT.name, HttpMethodEnum.PATCH.name -> return ScopeEnum.CHANGE.name
            HttpMethodEnum.POST.name -> return ScopeEnum.SAVE.name
            HttpMethodEnum.DELETE.name -> return ScopeEnum.DELETE.name
            else -> ""
        }
    }
}
