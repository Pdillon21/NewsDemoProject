package com.example.newsdemoproject.presentation

sealed class EventRequest {
    object RefreshEvent : EventRequest()
    data class IemClick(val item: Any?) : EventRequest()
}
