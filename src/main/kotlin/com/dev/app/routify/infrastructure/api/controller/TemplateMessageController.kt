package com.dev.app.routify.infrastructure.api.controller

import com.dev.app.routify.application.usecase.CreateEmailTemplateUseCase
import com.dev.app.routify.infrastructure.api.mapper.toApplicationDTO
import com.dev.app.routify.infrastructure.api.models.request.EmailTemplateRequestDTO
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/templates")
class TemplateMessageController(
    private val createEmailTemplateUseCase: CreateEmailTemplateUseCase
) {
    @PostMapping("/email")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody
        dto: EmailTemplateRequestDTO
    ) {
        createEmailTemplateUseCase.execute(dto.toApplicationDTO())
    }
}
