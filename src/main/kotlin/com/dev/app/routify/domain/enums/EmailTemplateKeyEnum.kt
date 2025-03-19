package com.dev.app.routify.domain.enums

enum class EmailTemplateKeyEnum {
    TEMPLATE_CONFIRMATION_CUSTOMER_ACCOUNT,
    TEMPLATE_PASSWORD_RESET,
    TEMPLATE_WELCOME_CUSTOMER;

    companion object {
        fun getEmailTemplate(value: String): EmailTemplateKeyEnum? {
            return entries.firstOrNull {
                it.name == value
            }
        }
    }
}
