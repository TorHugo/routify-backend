package com.dev.app.routify.infrastructure.api.models.request

import com.fasterxml.jackson.annotation.JsonProperty

data class EmailTemplateRequestDTO(
    @JsonProperty("template_key")
    val templateKey: String,

    @JsonProperty("subject")
    val subject: String,

    @JsonProperty("body")
    val body: String,

    @JsonProperty("is_html")
    val isHtml: Boolean,

    @JsonProperty("version")
    val version: String,

    @JsonProperty("parameters")
    val parameters: String,

    @JsonProperty("active")
    val active: Boolean
)
