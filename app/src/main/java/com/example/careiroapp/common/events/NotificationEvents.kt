package com.example.careiroapp.common.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NotificationEvents {

    private val _events = MutableSharedFlow<Events>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()


    fun sendEvent(eventType: Events) {
        _events.tryEmit(eventType)
    }

}