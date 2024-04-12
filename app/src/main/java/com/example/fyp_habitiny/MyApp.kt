package com.example.fyp_habitiny

import android.app.Application
import android.util.Log

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialization code
        initializeGlobalState()
    }

    private fun initializeGlobalState() {
        // Example initialization
        Log.d("MyApp", "Global state initialized")
        // TODO: Add your global initialization code here (e.g., setting up libraries)
    }
}