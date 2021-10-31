package com.example.movieapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.movieapp.util.PreferencesUtil
import dagger.hilt.android.HiltAndroidApp
import java.util.prefs.Preferences

@HiltAndroidApp
class App: Application() {

    lateinit var preferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        PreferencesUtil.initPreferences(applicationContext)
    }
}