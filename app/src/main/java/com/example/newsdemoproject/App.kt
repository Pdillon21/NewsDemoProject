package com.example.newsdemoproject

import android.app.Application
import com.example.newsdemoproject.data.remote.client.NewsDemoAppClientProvider
import com.example.newsdemoproject.utils.log.NewsDemoProjectLogger

class App : Application() {

    override fun onCreate() {
        NewsDemoAppClientProvider.init(BuildConfig.BASE_URL)
        NewsDemoProjectLogger.initLogger(BuildConfig.SHOULD_LOG)
        super.onCreate()
    }
}
