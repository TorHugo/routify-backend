package com.dev.app.routify.infrastructure.exception

import com.dev.app.routify.domain.exception.template.DomainException
import com.dev.app.routify.domain.exception.template.GatewayException
import com.dev.app.routify.domain.exception.template.InternalServerException
import com.dev.app.routify.domain.exception.template.InvalidArgumentException
import com.dev.app.routify.domain.exception.template.RepositoryException
import com.dev.app.routify.domain.exception.template.ServiceException
import com.dev.app.routify.infrastructure.api.models.response.DefaultResponseDTO
import com.dev.app.routify.infrastructure.exception.models.ExceptionData
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ValidationException
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messageSource: MessageSource
) {

    @ExceptionHandler(RepositoryException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRepositoryException(
        ex: RepositoryException,
        request: HttpServletRequest
    ): DefaultResponseDTO<ExceptionData> {
        return createErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            error = "Repository Error",
            message = messageSource.getMessage(ex.message!!, null, Locale.getDefault()),
            path = request.requestURI
        )
    }

    @ExceptionHandler(DomainException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDomainException(
        ex: DomainException,
        request: HttpServletRequest
    ): DefaultResponseDTO<ExceptionData> {
        return createErrorResponse(
            status = HttpStatus.CONFLICT,
            error = "Domain Error",
            message = messageSource.getMessage(ex.message!!, null, Locale.getDefault()),
            path = request.requestURI
        )
    }

    @ExceptionHandler(InternalServerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleDomainException(
        ex: InternalServerException,
        request: HttpServletRequest
    ): DefaultResponseDTO<ExceptionData> {
        return createErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            error = "Internal Error",
            message = messageSource.getMessage(ex.message!!, null, Locale.getDefault()),
            path = request.requestURI
        )
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    fun handleValidationException(
        ex: ValidationException,
        request: HttpServletRequest
    ): DefaultResponseDTO<ExceptionData> {
        return createErrorResponse(
            status = HttpStatus.PRECONDITION_FAILED,
            error = "Validation Error",
            message = messageSource.getMessage(ex.cause!!.message!!, null, Locale.getDefault()),
            path = request.requestURI
        )
    }

    @ExceptionHandler(ServiceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleServiceException(
        ex: ServiceException,
        request: HttpServletRequest
    ): DefaultResponseDTO<ExceptionData> {
        return createErrorResponse(
            status = HttpStatus.BAD_REQUEST,
            error = "Service Error",
            message = messageSource.getMessage(ex.message!!, null, Locale.getDefault()),
            path = request.requestURI
        )
    }

    @ExceptionHandler(InvalidArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidArgumentException(
        ex: InvalidArgumentException,
        request: HttpServletRequest
    ): DefaultResponseDTO<ExceptionData> {
        return createErrorResponse(
            status = HttpStatus.BAD_REQUEST,
            error = "Invalid Argument Error",
            message = messageSource.getMessage(ex.message!!, null, Locale.getDefault()),
            path = request.requestURI
        )
    }

    @ExceptionHandler(GatewayException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGatewayException(
        ex: GatewayException,
        request: HttpServletRequest
    ): DefaultResponseDTO<ExceptionData> {
        return createErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            error = "Gateway Error",
            message = messageSource.getMessage(ex.message!!, null, Locale.getDefault()),
            path = request.requestURI
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(
        ex: Exception,
        request: HttpServletRequest
    ): DefaultResponseDTO<ExceptionData> {
        return createErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            error = "Internal Server Error",
            message = "An unexpected error occurred",
            path = request.requestURI
        )
    }

    private fun createErrorResponse(
        status: HttpStatus,
        error: String,
        message: String?,
        path: String
    ): DefaultResponseDTO<ExceptionData> = DefaultResponseDTO(
        status = status.value(),
        data = ExceptionData(
            error = error,
            message = message ?: "No additional information available",
            path = path
        )
    )
}
