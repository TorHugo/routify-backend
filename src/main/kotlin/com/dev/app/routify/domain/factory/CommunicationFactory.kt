package com.dev.app.routify.domain.factory

import com.dev.app.routify.domain.enums.SubTypeEventEnum
import com.dev.app.routify.domain.service.CommunicationService

interface CommunicationFactory {
    fun getInstance(subTypeEvent: SubTypeEventEnum): CommunicationService
}