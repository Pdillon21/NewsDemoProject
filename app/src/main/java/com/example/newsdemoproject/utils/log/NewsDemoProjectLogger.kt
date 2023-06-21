package com.example.newsdemoproject.utils.log

import android.util.Log

object NewsDemoProjectLogger {
    private var shouldLog: Boolean = false

    fun initLogger(shouldLog: Boolean) {
        NewsDemoProjectLogger.shouldLog = shouldLog
    }

    fun log(title: String?, message: String?) {
        Log.d("ProjectDebug - $title", message ?: "No Message")
    }
}
