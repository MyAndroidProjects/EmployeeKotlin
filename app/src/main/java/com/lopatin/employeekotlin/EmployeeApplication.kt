package com.lopatin.employeekotlin

import android.app.Application
import android.content.Context

class EmployeeApplication : Application() {

    companion object {
        @get:Synchronized
        lateinit var instance: EmployeeApplication
            private set

        fun getApplicationContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}