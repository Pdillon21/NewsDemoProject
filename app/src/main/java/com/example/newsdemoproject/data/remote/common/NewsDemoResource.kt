package com.example.newsdemoproject.data.remote.common

sealed class NewsDemoResource<T>(open val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : NewsDemoResource<T>(data = data)
    class Error<T>(message: String?, data: T? = null) :
        NewsDemoResource<T>(data = data, message = message)

    class Loading<T>(data: T? = null) : NewsDemoResource<T>(data = data)
}
