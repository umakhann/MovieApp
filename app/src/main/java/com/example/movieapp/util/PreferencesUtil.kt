package com.example.movieapp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.movieapp.util.Constants.*
import com.example.movieapp.util.Constants.Companion.AUTO
import com.example.movieapp.util.Constants.Companion.NIGHT_MODE
import java.lang.NullPointerException

object PreferencesUtil {



    private var preferences: SharedPreferences? = null

    var nightMode: String?
        get() = readStringPreference(NIGHT_MODE, null)
        set(value) {
            if (value != null) {
                writeStringPreference(NIGHT_MODE, value)
            }
        }

    fun readStringPreference(key: String, default: String?): String? {
        assertPreferences()
        return preferences!!.getString(key, default)
    }

    fun initPreferences(context: Context) {
        preferences = context.getSharedPreferences("mainSharedPreferences", Context.MODE_PRIVATE)
    }

    /**
     * Writes [Int] value with the key provided
     * @param [key] key which points at the value
     * @param [int] value which is saved
     */
    fun writeStringPreference(key: String, value: String) {
        assertPreferences()
        val editor = preferences!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun assertPreferences() {
        if(preferences == null) throw NullPointerException("Preferences not defined!")
    }
}