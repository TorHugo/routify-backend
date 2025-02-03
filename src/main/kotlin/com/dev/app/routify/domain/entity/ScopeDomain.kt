package com.dev.app.routify.domain.entity

import com.dev.app.routify.domain.enums.ScopeKeyEnum
import java.io.Serializable

data class ScopeDomain(
    override val identifier: ScopeKeyEnum?,
    val path: String,
    val scopes: String
) : Serializable, AggregateRoot<ScopeKeyEnum?>()
