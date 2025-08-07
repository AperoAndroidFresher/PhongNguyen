package com.hoaiphong.composeui.comon

import android.content.Context
import androidx.core.content.edit

class UserSession(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USERNAME = "username"
    }

    fun saveUser(username: String) {
        prefs.edit { putString(KEY_USERNAME, username) }
    }

    fun getSavedUsername(): String? = prefs.getString(KEY_USERNAME, null)

    fun clear() {
        prefs.edit { clear() }
    }
}
