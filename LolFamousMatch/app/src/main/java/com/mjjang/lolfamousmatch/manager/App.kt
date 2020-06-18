package com.mjjang.lolfamousmatch.manager

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {
    lateinit var context: Context

    init{
        instance = this

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        private var instance: App? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}