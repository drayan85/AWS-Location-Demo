package com.aws.demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AWSDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}