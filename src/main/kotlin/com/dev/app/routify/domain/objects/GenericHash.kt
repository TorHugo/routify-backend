package com.dev.app.routify.domain.objects

import kotlin.random.Random

data class GenericHash(val value: String) {
    companion object {
        private const val DEFAULT_LENGTH: Int = 6
        private const val ZERO_LENGTH: Int = 0

        fun generate(length: Int? = null): GenericHash {
            var currentLength: Int = DEFAULT_LENGTH
            if (length == null || length == ZERO_LENGTH) currentLength = DEFAULT_LENGTH

            return GenericHash(
                buildString {
                    repeat(currentLength) {
                        if (it == 0) {
                            append(Random.nextInt(1, 10))
                        } else {
                            append(Random.nextInt(10))
                        }
                    }
                }
            )
        }
    }
}
