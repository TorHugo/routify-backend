package com.dev.app.routify.infrastructure.api.models.response

import com.dev.app.routify.domain.extension.toFormatted
import java.time.LocalDateTime

data class DefaultResponseDTO<T>(
    val status: Int,
    val data: T,
    val timestamp: String = LocalDateTime.now().toFormatted()
) {
    companion object {
        fun <T> success(data: T): DefaultResponseDTO<T> = DefaultResponseDTO(
            status = 200,
            data = data
        )

        fun <T> created(data: T): DefaultResponseDTO<T> = DefaultResponseDTO(
            status = 201,
            data = data
        )

        fun <T> unauthorized(data: T): DefaultResponseDTO<T> = DefaultResponseDTO(
            status = 401,
            data = data
        )

        fun <T> forbidden(data: T): DefaultResponseDTO<T> = DefaultResponseDTO(
            status = 403,
            data = data
        )

        fun <T> internalServerError(data: T): DefaultResponseDTO<T> = DefaultResponseDTO(
            status = 500,
            data = data
        )
    }
}
