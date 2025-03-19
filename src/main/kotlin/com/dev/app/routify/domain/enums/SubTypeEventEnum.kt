package com.dev.app.routify.domain.enums

enum class SubTypeEventEnum(val emailTemplateKeyEnum: EmailTemplateKeyEnum) {
    SUB_TYPE_CONFIRMATION_CUSTOMER(
        emailTemplateKeyEnum = EmailTemplateKeyEnum.TEMPLATE_CONFIRMATION_CUSTOMER_ACCOUNT
    ),
    SUB_TYPE_RESET_CUSTOMER_PASSWORD(
        emailTemplateKeyEnum = EmailTemplateKeyEnum.TEMPLATE_PASSWORD_RESET
    ),
    SUB_TYPE_WELCOME_CUSTOMER(
        emailTemplateKeyEnum = EmailTemplateKeyEnum.TEMPLATE_WELCOME_CUSTOMER
    )
}
