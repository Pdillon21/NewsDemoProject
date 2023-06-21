package com.example.newsdemoproject.data.remote.api

import com.example.newsdemoproject.data.remote.client.NewsDemoAppClientProvider

object ApiProvider {

    fun getGuardianApi(): GuardianApi {
        return NewsDemoAppClientProvider
            .getClient()
            .create(GuardianApi::class.java)
    }
}
