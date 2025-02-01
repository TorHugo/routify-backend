package com.dev.app.routify.domain.entity

abstract class AggregateRoot<T> {
    abstract val identifier: T

    fun getIdentifier(): String {
        return identifier.toString()
    }
}
