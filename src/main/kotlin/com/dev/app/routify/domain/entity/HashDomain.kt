package com.dev.app.routify.domain.entity

import com.dev.app.routify.domain.objects.ExpirationDate
import com.dev.app.routify.domain.objects.GenericHash
import java.io.Serializable

data class HashDomain(
    override val identifier: GenericHash,
    val expiration: ExpirationDate
) : Serializable, AggregateRoot<GenericHash>()
