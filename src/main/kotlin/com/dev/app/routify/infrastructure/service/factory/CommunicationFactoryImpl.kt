package com.dev.app.routify.infrastructure.service.factory

import com.dev.app.routify.domain.enums.SubTypeEventEnum
import com.dev.app.routify.domain.factory.CommunicationFactory
import com.dev.app.routify.domain.service.CommunicationService
import com.dev.app.routify.infrastructure.service.template.ConfirmedCustomerCommunicationTemplate
import com.dev.app.routify.infrastructure.service.template.ResetCustomerPasswordCommunicationTemplate
import org.springframework.stereotype.Component

@Component
class CommunicationFactoryImpl(
    private val confirmedCustomerCommunicationTemplate: ConfirmedCustomerCommunicationTemplate,
    private val resetCustomerPasswordCommunicationTemplate: ResetCustomerPasswordCommunicationTemplate
) : CommunicationFactory {
    override fun getInstance(subTypeEvent: SubTypeEventEnum): CommunicationService {
        return when (subTypeEvent) {
            SubTypeEventEnum.SUB_TYPE_CONFIRMATION_CUSTOMER -> confirmedCustomerCommunicationTemplate
            SubTypeEventEnum.SUB_TYPE_RESET_CUSTOMER_PASSWORD -> resetCustomerPasswordCommunicationTemplate
            SubTypeEventEnum.SUB_TYPE_WELCOME_CUSTOMER -> confirmedCustomerCommunicationTemplate
        }
    }
}
